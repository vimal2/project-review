package com.revplay.controller;

import com.revplay.dto.request.UserProfileUpdateRequest;
import com.revplay.dto.response.UserProfileResponse;
import com.revplay.dto.response.UserStatsResponse;
import com.revplay.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getMyProfile(
            @RequestParam String username) {
        String normalizedUsername = normalizeUsername(username);
        return ResponseEntity.ok(userService.getProfile(normalizedUsername));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateMyProfile(
            @RequestParam String username,
            @RequestBody UserProfileUpdateRequest updatedUser) {
        String normalizedUsername = normalizeUsername(username);
        return ResponseEntity.ok(userService.updateProfile(normalizedUsername, updatedUser));
    }

    @GetMapping("/stats")
    public ResponseEntity<UserStatsResponse> getMyStats(
            @RequestParam(required = false) String username) {
        String normalizedUsername = resolveUsername(username);
        return ResponseEntity.ok(userService.getStats(normalizedUsername));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserProfileResponse> getProfile(@PathVariable String username) {
        return ResponseEntity.ok(userService.getProfile(normalizeUsername(username)));
    }

    private String normalizeUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return "satish";
        }
        return username.trim();
    }

    private String resolveUsername(String username) {
        if (username != null && !username.trim().isEmpty()) {
            return username.trim();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String principalName = authentication.getName();
            if (principalName != null
                    && !principalName.trim().isEmpty()
                    && !"anonymousUser".equalsIgnoreCase(principalName.trim())) {
                return principalName.trim();
            }
        }

        return "satish";
    }
}
