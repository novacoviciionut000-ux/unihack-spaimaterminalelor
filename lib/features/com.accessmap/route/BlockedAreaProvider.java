package com.accessmap.route;

import com.accessmap.common.Coordinate;

import java.util.List;

public interface BlockedAreaProvider {
    List<BlockedArea> activeBlockedAreas(Coordinate from, Coordinate to);
}