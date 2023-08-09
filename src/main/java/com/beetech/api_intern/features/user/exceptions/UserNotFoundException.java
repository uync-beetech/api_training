package com.beetech.api_intern.features.user.exceptions;

import com.beetech.api_intern.common.exceptions.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    private static UserNotFoundException instance = null;

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public static UserNotFoundException getInstance() {
        if (instance == null) {
            instance = new UserNotFoundException("User not found!");
        }
        return instance;
    }
}
