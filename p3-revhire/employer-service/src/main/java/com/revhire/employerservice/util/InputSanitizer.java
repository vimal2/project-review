package com.revhire.employerservice.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Utility class for sanitizing user input to prevent XSS and injection attacks
 */
@Component
public class InputSanitizer {

    // Pattern to match potentially dangerous HTML/script tags
    private static final Pattern HTML_SCRIPT_PATTERN = Pattern.compile(
        "<script[^>]*>.*?</script>|<iframe[^>]*>.*?</iframe>|javascript:|onerror=|onload=",
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL
    );

    // Pattern to match SQL injection attempts
    private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile(
        "('|(\\-\\-)|(;)|(\\|\\|)|(\\*))",
        Pattern.CASE_INSENSITIVE
    );

    /**
     * Sanitize input string by removing potentially dangerous content
     * @param input the input string to sanitize
     * @return sanitized string
     */
    public static String sanitize(String input) {
        if (input == null || input.trim().isEmpty()) {
            return input;
        }

        // Trim whitespace
        String sanitized = input.trim();

        // Remove script tags and dangerous attributes
        sanitized = HTML_SCRIPT_PATTERN.matcher(sanitized).replaceAll("");

        // Replace common HTML entities
        sanitized = sanitized.replace("<", "&lt;")
                             .replace(">", "&gt;")
                             .replace("\"", "&quot;")
                             .replace("'", "&#x27;")
                             .replace("/", "&#x2F;");

        return sanitized;
    }

    /**
     * Sanitize input for SQL queries (basic protection)
     * Note: Always use prepared statements for real SQL protection
     * @param input the input string to sanitize
     * @return sanitized string
     */
    public static String sanitizeForSQL(String input) {
        if (input == null || input.trim().isEmpty()) {
            return input;
        }

        // Remove potentially dangerous SQL characters
        return input.replace("'", "''")
                   .replace("--", "")
                   .replace(";", "")
                   .replace("/*", "")
                   .replace("*/", "");
    }

    /**
     * Validate that input doesn't contain SQL injection patterns
     * @param input the input string to validate
     * @return true if input is safe, false otherwise
     */
    public static boolean isSafeFromSQLInjection(String input) {
        if (input == null || input.trim().isEmpty()) {
            return true;
        }

        return !SQL_INJECTION_PATTERN.matcher(input).find();
    }

    /**
     * Sanitize email input
     * @param email the email to sanitize
     * @return sanitized email
     */
    public static String sanitizeEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return email;
        }

        // Basic email sanitization - remove dangerous characters
        return email.trim()
                   .toLowerCase()
                   .replaceAll("[^a-z0-9@._-]", "");
    }

    /**
     * Sanitize URL input
     * @param url the URL to sanitize
     * @return sanitized URL
     */
    public static String sanitizeUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return url;
        }

        // Remove javascript: and other dangerous protocols
        String sanitized = url.trim().toLowerCase();

        if (sanitized.startsWith("javascript:") ||
            sanitized.startsWith("data:") ||
            sanitized.startsWith("vbscript:")) {
            return "";
        }

        return url.trim();
    }

    /**
     * Truncate string to maximum length
     * @param input the input string
     * @param maxLength maximum allowed length
     * @return truncated string
     */
    public static String truncate(String input, int maxLength) {
        if (input == null || input.length() <= maxLength) {
            return input;
        }

        return input.substring(0, maxLength);
    }
}
