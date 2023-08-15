package com.beetech.api_intern.features.carts.exceptions;

import com.beetech.api_intern.common.exceptions.EntityNotFoundException;

public class CartNotFoundException extends EntityNotFoundException {
    private static CartNotFoundException instance = null;

    public CartNotFoundException(String message) {
        super(message);
    }

    public static CartNotFoundException getInstance() {
        if (instance == null) {
            instance = new CartNotFoundException("Cart not found!");
        }
        return instance;
    }
}
