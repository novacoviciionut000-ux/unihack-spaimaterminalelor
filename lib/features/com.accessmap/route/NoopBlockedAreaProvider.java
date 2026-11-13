package com.accessmap.route;

import com.accessmap.common.Coordinate;
import org.springframework.stereotype.Component;

import java.util.List;

/** Temporar. ȘTERGE această clasă când există implementarea reală cu rapoarte. */
@Component
public class NoopBlockedAreaProvider implements BlockedAreaProvider {
    @Override
    public List<BlockedArea> activeBlockedAreas(Coordinate from, Coordinate to) {
        return List.of();
    }
}