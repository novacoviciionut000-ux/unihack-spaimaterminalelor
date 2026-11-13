package com.accessmap.route;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Component
public class GraphHopperClient {

    private final RestClient client;

    public GraphHopperClient(RestClient.Builder builder,
                             @Value("${graphhopper.url:http://localhost:8989}") String url) {
        this.client = builder.baseUrl(url).build();
    }

    public GhPath route(Map<String, Object> body) {
        try {
            GhResponse response = client.post()
                    .uri("/route")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .body(GhResponse.class);

            if (response == null || response.paths() == null || response.paths().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nu s-a găsit nicio rută");
            }
            return response.paths().get(0);

        } catch (RestClientResponseException e) {
            // GraphHopper răspunde 400 când un punct e în afara hărții sau nu există rută
            if (e.getStatusCode().is4xxClientError()) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "GraphHopper: " + e.getResponseBodyAsString());
            }
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "GraphHopper nu răspunde corect");
        }
    }

    // --- formatul răspunsului GraphHopper ---
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record GhResponse(List<GhPath> paths) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record GhPath(double distance, long time, double ascend, double descend,
                         GhPoints points, List<GhInstruction> instructions) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record GhPoints(List<List<Double>> coordinates) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record GhInstruction(String text, double distance, long time) {}
}