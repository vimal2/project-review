package com.revplay.controller;

import com.revplay.dto.response.ApiResponse;
import com.revplay.dto.response.ListeningHistoryResponse;
import com.revplay.service.ListeningHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@CrossOrigin(origins = "http://localhost:4200")
public class ListeningHistoryController {

    private static final Logger log = LoggerFactory.getLogger(ListeningHistoryController.class);
    private final ListeningHistoryService listeningHistoryService;

    public ListeningHistoryController(ListeningHistoryService listeningHistoryService) {
        this.listeningHistoryService = listeningHistoryService;
    }

    @GetMapping("/recent")
    public List<ListeningHistoryResponse> getRecentHistory(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "50") int limit
    ) {
        log.info("Recent history request. userId={}, limit={}", userId, limit);
        return listeningHistoryService.getRecentHistory(userId, limit);
    }

    @DeleteMapping
    public ApiResponse<Void> clearHistory(@RequestParam Long userId) {
        log.info("Clear history request. userId={}", userId);
        long removed = listeningHistoryService.clearHistory(userId);
        return new ApiResponse<>(true, "Removed " + removed + " history record(s)", null);
    }
}
