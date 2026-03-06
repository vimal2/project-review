package com.revhire.employerservice.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when access to a resource is forbidden
 */
public class ForbiddenException extends ApiException {

    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(HttpStatus.FORBIDDEN, message, cause);
    }
}
