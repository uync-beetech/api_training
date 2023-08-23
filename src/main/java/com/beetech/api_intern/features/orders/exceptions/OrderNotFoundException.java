package com.beetech.api_intern.features.orders.exceptions;

import com.beetech.api_intern.common.exceptions.EntityNotFoundException;

public class OrderNotFoundException extends EntityNotFoundException {
    private static OrderNotFoundException instance = null;

    public OrderNotFoundException(String message) {
        super(message);
    }

    public static OrderNotFoundException getInstance() {
        if (instance == null) {
            instance = new OrderNotFoundException("Order not found!");
        }
        return instance;
    }
}
