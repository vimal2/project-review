package com.revature.revhire.authservice.controller;

import com.revature.revhire.authservice.dto.*;
import com.revature.revhire.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Register a new user
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        log.info("Registration request received for username: {}", request.getUsername());
        AuthResponse response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Login user
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("Login request received for username: {}", request.getUsername());
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Logout user
     * POST /api/auth/logout
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout() {
        log.info("Logout request received");
        ApiResponse response = authService.logout();
        return ResponseEntity.ok(response);
    }

    /**
     * Change password
     * POST /api/auth/change-password
     */
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            Authentication authentication
    ) {
        String username = authentication.getName();
        log.info("Change password request received for username: {}", username);
        ApiResponse response = authService.changePassword(username, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Forgot password - generate reset token
     * POST /api/auth/forgot-password
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request
    ) {
        log.info("Forgot password request received for email: {}", request.getEmail());
        ForgotPasswordResponse response = authService.forgotPassword(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Reset password using reset token
     * POST /api/auth/reset-password
     */
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        log.info("Reset password request received");
        ApiResponse response = authService.resetPassword(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Update profile completion
     * POST /api/auth/profile-completion/{userId}
     */
    @PostMapping("/profile-completion/{userId}")
    public ResponseEntity<ApiResponse> updateProfileCompletion(
            @PathVariable Long userId,
            @Valid @RequestBody ProfileCompletionRequest request
    ) {
        log.info("Profile completion request received for user ID: {}", userId);
        ApiResponse response = authService.updateProfileCompletion(userId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get user by ID (for inter-service communication)
     * GET /api/auth/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        log.info("Get user request received for ID: {}", userId);
        UserDto userDto = authService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }

    /**
     * Validate JWT token (for inter-service communication)
     * GET /api/auth/validate?token={token}
     */
    @GetMapping("/validate")
    public ResponseEntity<ApiResponse> validateToken(@RequestParam String token) {
        log.info("Token validation request received");
        ApiResponse response = authService.validateToken(token);
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint
     * GET /api/auth/health
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("Auth Service is running"));
    }
}
