package com.accessmap.route;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum RoutingProfile {
    WHEELCHAIR("wheelchair"),
    LIMITED_MOBILITY("limited_mobility"),
    LOW_VISION("low_vision");

    private final String ghName; // trebuie să fie identic cu numele din config.yml

    RoutingProfile(String ghName) { this.ghName = ghName; }

    public String ghName() { return ghName; }

    public static RoutingProfile from(String value) {
        for (RoutingProfile p : values()) {
            if (p.ghName.equalsIgnoreCase(value)) return p;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profil necunoscut: " + value);
    }
}