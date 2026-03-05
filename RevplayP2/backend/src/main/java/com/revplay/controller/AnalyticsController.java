package com.revplay.controller;

import com.revplay.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/artist/analytics")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/{artistId}/overview")
    public Object overview(@PathVariable Long artistId) {
        return analyticsService.getOverview(artistId);
    }

    @GetMapping("/{artistId}/songs")
    public Object songs(@PathVariable Long artistId) {
        return analyticsService.getSongPerformance(artistId);
    }

    @GetMapping("/{artistId}/top")
    public Object top(@PathVariable Long artistId) {
        return analyticsService.getTopSongs(artistId);
    }

    @GetMapping("/{artistId}/trends")
    public Object trends(
            @PathVariable Long artistId,
            @RequestParam(defaultValue = "monthly") String type
    ) {
        return analyticsService.getListeningTrends(artistId, type);
    }

    @GetMapping("/{artistId}/top-listeners")
    public Object topListeners(@PathVariable Long artistId) {
        return analyticsService.getTopListeners(artistId);
    }
}
