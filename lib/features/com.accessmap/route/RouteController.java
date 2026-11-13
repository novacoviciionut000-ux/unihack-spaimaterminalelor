package com.accessmap.route;

import com.accessmap.common.Coordinate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    private final RouteService service;

    public RouteController(RouteService service) {
        this.service = service;
    }

    @GetMapping
    public RouteResponse route(@RequestParam String from,
                               @RequestParam String to,
                               @RequestParam(defaultValue = "wheelchair") String profile) {
        return service.route(parse(from), parse(to), RoutingProfile.from(profile));
    }

    /** Acceptă "45.7537,21.2257" (lat,lng). */
    private Coordinate parse(String value) {
        try {
            String[] parts = value.split(",");
            return new Coordinate(Double.parseDouble(parts[0].trim()), Double.parseDouble(parts[1].trim()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Coordonate invalide: '" + value + "'. Format: lat,lng");
        }
    }
}