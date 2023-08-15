package com.beetech.api_intern.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnauthorizedException extends ResponseStatusException {
    private static UnauthorizedException instance = null;
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, message, cause);
    }

    public static UnauthorizedException getInstance() {
        if (instance == null) {
            instance = new UnauthorizedException("Unauthorized");
        }
        return instance;
    }
}
