package com.accessmap.route;

import com.accessmap.common.Coordinate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RouteService {

    private final GraphHopperClient graphHopper;
    private final BlockedAreaProvider blockedAreaProvider;

    public RouteService(GraphHopperClient graphHopper, BlockedAreaProvider blockedAreaProvider) {
        this.graphHopper = graphHopper;
        this.blockedAreaProvider = blockedAreaProvider;
    }

    public RouteResponse route(Coordinate from, Coordinate to, RoutingProfile profile) {
        List<BlockedArea> blocked = blockedAreaProvider.activeBlockedAreas(from, to);

        Map<String, Object> body = new LinkedHashMap<>();
        // ATENȚIE: GraphHopper vrea [lng, lat], nu [lat, lng]
        body.put("points", List.of(
                List.of(from.lng(), from.lat()),
                List.of(to.lng(), to.lat())));
        body.put("profile", profile.ghName());
        body.put("points_encoded", false);   // ne dă coordonate simple, nu polyline codat
        body.put("instructions", true);
        body.put("locale", "ro");

        if (!blocked.isEmpty()) {
            body.put("ch.disable", true);    // custom_model per cerere funcționează doar fără CH
            body.put("custom_model", buildCustomModel(blocked));
        }

        GraphHopperClient.GhPath path = graphHopper.route(body);

        List<RouteResponse.Step> steps = path.instructions() == null ? List.of()
                : path.instructions().stream()
                    .map(i -> new RouteResponse.Step(i.text(), i.distance(), i.time()))
                    .toList();

        return new RouteResponse(
                profile.ghName(),
                path.distance(),
                path.time(),
                path.ascend(),
                path.descend(),
                blocked.size(),
                path.points().coordinates(),
                steps);
    }

    /** Transformă zonele blocate în poligoane + regulă de prioritate 0. */
    private Map<String, Object> buildCustomModel(List<BlockedArea> blocked) {
        List<Map<String, Object>> features = new ArrayList<>();
        List<String> conditions = new ArrayList<>();

        for (int i = 0; i < blocked.size(); i++) {
            String id = "blocked" + i;
            conditions.add("in_" + id);

            Map<String, Object> geometry = Map.of(
                    "type", "Polygon",
                    "coordinates", List.of(circle(blocked.get(i), 16)));

            features.add(Map.of(
                    "type", "Feature",
                    "id", id,
                    "properties", Map.of(),
                    "geometry", geometry));
        }

        Map<String, Object> rule = Map.of(
                "if", String.join(" || ", conditions),
                "multiply_by", 0);

        return Map.of(
                "priority", List.of(rule),
                "areas", Map.of("type", "FeatureCollection", "features", features));
    }

    /** Aproximează un cerc cu un poligon cu `n` laturi. Rezultat: inel închis de [lng, lat]. */
    private List<List<Double>> circle(BlockedArea area, int n) {
        double lat = area.center().lat();
        double lng = area.center().lng();
        double dLat = area.radiusMeters() / 111_320.0;
        double dLng = area.radiusMeters() / (111_320.0 * Math.cos(Math.toRadians(lat)));

        List<List<Double>> ring = new ArrayList<>();
        for (int k = 0; k < n; k++) {
            double angle = 2 * Math.PI * k / n;
            ring.add(List.of(lng + dLng * Math.cos(angle), lat + dLat * Math.sin(angle)));
        }
        ring.add(ring.get(0)); // inelul trebuie închis
        return ring;
    }
}