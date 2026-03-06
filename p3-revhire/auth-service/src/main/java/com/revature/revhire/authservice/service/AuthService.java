package com.revature.revhire.authservice.service;

import com.revature.revhire.authservice.dto.*;
import com.revature.revhire.authservice.entity.PasswordResetToken;
import com.revature.revhire.authservice.entity.User;
import com.revature.revhire.authservice.exception.BadRequestException;
import com.revature.revhire.authservice.exception.ConflictException;
import com.revature.revhire.authservice.exception.ForbiddenException;
import com.revature.revhire.authservice.exception.NotFoundException;
import com.revature.revhire.authservice.repository.PasswordResetTokenRepository;
import com.revature.revhire.authservice.repository.UserRepository;
import com.revature.revhire.authservice.util.InputSanitizer;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final InputSanitizer inputSanitizer;

    public AuthService(UserRepository userRepository, PasswordResetTokenRepository passwordResetTokenRepository,
                      PasswordEncoder passwordEncoder, JwtService jwtService,
                      CustomUserDetailsService userDetailsService, AuthenticationManager authenticationManager,
                      InputSanitizer inputSanitizer) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.inputSanitizer = inputSanitizer;
    }

    /**
     * Register a new user
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Attempting to register user: {}", request.getUsername());

        // Sanitize inputs
        String username = inputSanitizer.sanitize(request.getUsername());
        String email = inputSanitizer.sanitize(request.getEmail());

        // Validate inputs
        if (!inputSanitizer.isValidUsername(username)) {
            throw new BadRequestException("Invalid username format");
        }

        if (!inputSanitizer.isValidEmail(email)) {
            throw new BadRequestException("Invalid email format");
        }

        if (!inputSanitizer.isValidPassword(request.getPassword())) {
            throw new BadRequestException("Password must be at least 6 characters");
        }

        // Check if username already exists
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new ConflictException("Username already exists");
        }

        // Check if email already exists
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new ConflictException("Email already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setFullName(inputSanitizer.sanitize(request.getFullName()));
        user.setMobileNumber(inputSanitizer.sanitize(request.getMobileNumber()));
        user.setSecurityQuestion(inputSanitizer.sanitize(request.getSecurityQuestion()));
        user.setSecurityAnswer(passwordEncoder.encode(inputSanitizer.sanitize(request.getSecurityAnswer())));
        user.setProfileCompleted(false);

        user = userRepository.save(user);
        log.info("User registered successfully: {}", user.getUsername());

        // Generate JWT token
        var userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .profileCompleted(user.getProfileCompleted())
                .message("Registration successful")
                .build();
    }

    /**
     * Login user
     */
    public AuthResponse login(LoginRequest request) {
        log.info("Attempting login for user: {}", request.getUsername());

        String username = inputSanitizer.sanitize(request.getUsername());

        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, request.getPassword())
        );

        // Load user details
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Generate JWT token
        var userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtService.generateToken(userDetails);

        log.info("User logged in successfully: {}", user.getUsername());

        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .profileCompleted(user.getProfileCompleted())
                .message("Login successful")
                .build();
    }

    /**
     * Logout user (client-side token removal)
     */
    public ApiResponse logout() {
        // Clear security context
        SecurityContextHolder.clearContext();
        log.info("User logged out successfully");
        return ApiResponse.success("Logout successful");
    }

    /**
     * Change user password
     */
    @Transactional
    public ApiResponse changePassword(String username, ChangePasswordRequest request) {
        log.info("Attempting to change password for user: {}", username);

        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Verify current password
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("Current password is incorrect");
        }

        // Validate new password
        if (!inputSanitizer.isValidPassword(request.getNewPassword())) {
            throw new BadRequestException("New password must be at least 6 characters");
        }

        // Check if new password is same as current
        if (request.getCurrentPassword().equals(request.getNewPassword())) {
            throw new BadRequestException("New password must be different from current password");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        log.info("Password changed successfully for user: {}", username);
        return ApiResponse.success("Password changed successfully");
    }

    /**
     * Forgot password - generate reset token
     */
    @Transactional
    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request) {
        log.info("Processing forgot password request for email: {}", request.getEmail());

        String email = inputSanitizer.sanitize(request.getEmail());

        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new NotFoundException("User not found with this email"));

        // Verify security answer
        if (!passwordEncoder.matches(request.getSecurityAnswer(), user.getSecurityAnswer())) {
            throw new ForbiddenException("Security answer is incorrect");
        }

        // Delete existing tokens for this user
        passwordResetTokenRepository.deleteByUser(user);

        // Generate reset token
        String resetToken = UUID.randomUUID().toString();
        LocalDateTime expiryAt = LocalDateTime.now().plusMinutes(30);

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(resetToken);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryAt(expiryAt);

        passwordResetTokenRepository.save(passwordResetToken);

        log.info("Password reset token generated for user: {}", user.getUsername());

        return ForgotPasswordResponse.builder()
                .resetToken(resetToken)
                .message("Password reset token generated successfully")
                .expiresInMinutes(30L)
                .build();
    }

    /**
     * Reset password using reset token
     */
    @Transactional
    public ApiResponse resetPassword(ResetPasswordRequest request) {
        log.info("Attempting to reset password using token");

        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(request.getResetToken())
                .orElseThrow(() -> new NotFoundException("Invalid or expired reset token"));

        // Check if token is expired
        if (resetToken.isExpired()) {
            passwordResetTokenRepository.delete(resetToken);
            throw new BadRequestException("Reset token has expired");
        }

        // Validate new password
        if (!inputSanitizer.isValidPassword(request.getNewPassword())) {
            throw new BadRequestException("New password must be at least 6 characters");
        }

        // Update password
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        // Delete used token
        passwordResetTokenRepository.delete(resetToken);

        log.info("Password reset successfully for user: {}", user.getUsername());
        return ApiResponse.success("Password reset successfully");
    }

    /**
     * Update profile completion status
     */
    @Transactional
    public ApiResponse updateProfileCompletion(Long userId, ProfileCompletionRequest request) {
        log.info("Updating profile completion for user ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setFullName(inputSanitizer.sanitize(request.getFullName()));
        user.setMobileNumber(inputSanitizer.sanitize(request.getMobileNumber()));
        user.setLocation(inputSanitizer.sanitize(request.getLocation()));
        user.setEmploymentStatus(request.getEmploymentStatus());
        user.setProfileCompleted(true);

        userRepository.save(user);

        log.info("Profile completed for user: {}", user.getUsername());
        return ApiResponse.success("Profile completed successfully");
    }

    /**
     * Get user by ID (for inter-service communication)
     */
    public UserDto getUserById(Long userId) {
        log.info("Fetching user details for ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .fullName(user.getFullName())
                .mobileNumber(user.getMobileNumber())
                .location(user.getLocation())
                .employmentStatus(user.getEmploymentStatus())
                .profileCompleted(user.getProfileCompleted())
                .createdAt(user.getCreatedAt())
                .build();
    }

    /**
     * Validate JWT token (for inter-service communication)
     */
    public ApiResponse validateToken(String token) {
        try {
            String username = jwtService.extractUsername(token);
            var userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(token, userDetails)) {
                return ApiResponse.success("Token is valid", username);
            } else {
                return ApiResponse.error("Token is invalid");
            }
        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage());
            return ApiResponse.error("Token validation failed");
        }
    }
}
