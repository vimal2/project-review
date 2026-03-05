package com.revplay.controller;

import com.revplay.dto.request.PlaySongRequest;
import com.revplay.dto.response.ApiResponse;
import com.revplay.service.ListeningHistoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player")
@CrossOrigin(origins = "http://localhost:4200")
public class PlayerController {

    private static final Logger log = LoggerFactory.getLogger(PlayerController.class);
    private final ListeningHistoryService listeningHistoryService;

    public PlayerController(ListeningHistoryService listeningHistoryService) {
        this.listeningHistoryService = listeningHistoryService;
    }

    @PostMapping("/play")
    public ApiResponse<Void> playSong(@Valid @RequestBody PlaySongRequest request) {
        log.info("Play event received. userId={}, songId={}", request.getUserId(), request.getSongId());
        listeningHistoryService.recordPlay(request.getUserId(), request.getSongId());
        return new ApiResponse<>(true, "Play event recorded successfully", null);
    }
}
