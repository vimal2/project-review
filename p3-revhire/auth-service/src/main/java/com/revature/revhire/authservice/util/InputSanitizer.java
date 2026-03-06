package com.revature.revhire.authservice.util;

import org.springframework.stereotype.Component;

@Component
public class InputSanitizer {

    /**
     * Sanitizes input to prevent XSS and SQL injection attacks
     * @param input the input string to sanitize
     * @return sanitized string
     */
    public String sanitize(String input) {
        if (input == null) {
            return null;
        }

        // Remove HTML tags
        String sanitized = input.replaceAll("<[^>]*>", "");

        // Remove SQL injection patterns
        sanitized = sanitized.replaceAll("(?i)(script|select|insert|update|delete|drop|create|alter|exec|execute)", "");

        // Trim whitespace
        sanitized = sanitized.trim();

        return sanitized;
    }

    /**
     * Validates email format
     * @param email the email to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    /**
     * Validates username format
     * @param username the username to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidUsername(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }
        // Username must be 3-50 characters, alphanumeric with underscores
        String usernameRegex = "^[A-Za-z0-9_]{3,50}$";
        return username.matches(usernameRegex);
    }

    /**
     * Validates password strength
     * @param password the password to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        // Password must be at least 6 characters
        return true;
    }
}
