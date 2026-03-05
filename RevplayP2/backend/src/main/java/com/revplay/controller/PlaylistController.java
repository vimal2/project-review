package com.revplay.controller;

import com.revplay.dto.request.PlaylistCreateRequest;
import com.revplay.dto.request.PlaylistUpdateRequest;
import com.revplay.dto.response.PlaylistResponse;
import com.revplay.service.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<PlaylistResponse> createPlaylist(
            @RequestParam String username,
            @RequestBody PlaylistCreateRequest request) {
        String normalizedUsername = normalizeUsername(username);
        String name = request.getName() == null ? "" : request.getName().trim();
        if (name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Playlist name is required");
        }

        return ResponseEntity.ok(
                playlistService.createPlaylist(normalizedUsername, name, request.getDescription())
        );
    }

    @GetMapping
    public ResponseEntity<List<PlaylistResponse>> getPlaylists(
            @RequestParam String username) {
        return ResponseEntity.ok(playlistService.getUserPlaylists(normalizeUsername(username)));
    }

    @PutMapping("/{playlistId}")
    public ResponseEntity<PlaylistResponse> updatePlaylist(
            @RequestParam String username,
            @PathVariable Long playlistId,
            @RequestBody PlaylistUpdateRequest request) {
        return ResponseEntity.ok(
                playlistService.updatePlaylist(normalizeUsername(username), playlistId, request.getName(), request.getDescription())
        );
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<Void> deletePlaylist(
            @RequestParam String username,
            @PathVariable Long playlistId) {
        playlistService.deletePlaylist(normalizeUsername(username), playlistId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<PlaylistResponse> addSong(
            @RequestParam String username,
            @PathVariable Long playlistId,
            @PathVariable Long songId) {
        if (songId == null || songId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Song ID must be positive");
        }
        return ResponseEntity.ok(playlistService.addSong(normalizeUsername(username), playlistId, songId));
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<PlaylistResponse> removeSong(
            @RequestParam String username,
            @PathVariable Long playlistId,
            @PathVariable Long songId) {
        return ResponseEntity.ok(playlistService.removeSong(normalizeUsername(username), playlistId, songId));
    }

    private String normalizeUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return "satish";
        }
        return username.trim();
    }
}
