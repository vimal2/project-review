package com.revhire.employerservice.exception;

import com.revhire.employerservice.dto.ApiResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle ApiException and its subclasses
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Object>> handleApiException(ApiException ex, WebRequest request) {
        log.error("API Exception: {}", ex.getMessage(), ex);

        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, ex.getStatus());
    }

    /**
     * Handle BadRequestException
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequestException(BadRequestException ex, WebRequest request) {
        log.error("Bad Request: {}", ex.getMessage());

        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle NotFoundException
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFoundException(NotFoundException ex, WebRequest request) {
        log.error("Not Found: {}", ex.getMessage());

        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle ForbiddenException
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiResponse<Object>> handleForbiddenException(ForbiddenException ex, WebRequest request) {
        log.error("Forbidden: {}", ex.getMessage());

        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    /**
     * Handle validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
                .success(false)
                .message("Validation failed")
                .data(errors)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle Feign client exceptions (errors from other services)
     */
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ApiResponse<Object>> handleFeignException(FeignException ex, WebRequest request) {
        log.error("Feign client error: {}", ex.getMessage());

        String message = "Error communicating with external service";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ex.status() == 404) {
            message = "Requested resource not found in external service";
            status = HttpStatus.NOT_FOUND;
        } else if (ex.status() == 400) {
            message = "Bad request to external service";
            status = HttpStatus.BAD_REQUEST;
        } else if (ex.status() == 503) {
            message = "External service is unavailable";
            status = HttpStatus.SERVICE_UNAVAILABLE;
        }

        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .message(message)
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, status);
    }

    /**
     * Handle all other exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGlobalException(Exception ex, WebRequest request) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);

        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .message("An unexpected error occurred: " + ex.getMessage())
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        log.error("Illegal argument: {}", ex.getMessage());

        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
