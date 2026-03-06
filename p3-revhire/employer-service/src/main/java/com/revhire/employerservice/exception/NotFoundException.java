package com.revhire.employerservice.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a requested resource is not found
 */
public class NotFoundException extends ApiException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(HttpStatus.NOT_FOUND, message, cause);
    }
}
