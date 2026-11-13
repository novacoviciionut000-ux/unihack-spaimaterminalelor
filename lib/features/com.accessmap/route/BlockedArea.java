package com.accessmap.route;

import com.accessmap.common.Coordinate;

/** Zonă ocolită de rută (ex: un raport de tip "lift stricat"). */
public record BlockedArea(Coordinate center, double radiusMeters) {}