package com.revplay.controller;

import com.revplay.service.FavoriteService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/{songId}")
    public ResponseEntity<String> addFavorite(
            @PathVariable Long songId,
            @RequestParam String username) {
        favoriteService.addFavorite(normalizeUsername(username), songId);
        return ResponseEntity.ok("Added");
    }

    @DeleteMapping("/{songId}")
    public ResponseEntity<String> removeFavorite(
            @PathVariable Long songId,
            @RequestParam String username) {
        favoriteService.removeFavorite(normalizeUsername(username), songId);
        return ResponseEntity.ok("Removed");
    }

    @GetMapping
    public ResponseEntity<?> getFavorites(
            @RequestParam String username) {
        return ResponseEntity.ok(
                favoriteService.getFavorites(normalizeUsername(username))
        );
    }

    private String normalizeUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return "satish";
        }
        return username.trim();
    }
}
