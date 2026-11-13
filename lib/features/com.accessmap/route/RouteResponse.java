package com.accessmap.route;

import java.util.List;

public record RouteResponse(
        String profile,
        double distanceMeters,
        long timeMillis,
        double ascendMeters,
        double descendMeters,
        int blockedAreasUsed,
        List<List<Double>> coordinates,
        List<Step> steps) {

    public record Step(String text, double distanceMeters, long timeMillis) {}
}