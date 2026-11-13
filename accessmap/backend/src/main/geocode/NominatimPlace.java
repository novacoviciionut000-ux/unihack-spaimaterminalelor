package com.accessmap.geocode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Un rezultat de la Nominatim. lat și lon vin ca String-uri, le convertim în service. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record NominatimPlace(
        String lat,
        String lon,
        @JsonProperty("display_name") String displayName) {}