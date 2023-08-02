package com.beetech.api_intern.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ConflictException extends ResponseStatusException {
    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }

    public ConflictException(String message, Throwable cause) {
        super(HttpStatus.CONFLICT, message, cause);
    }
}
