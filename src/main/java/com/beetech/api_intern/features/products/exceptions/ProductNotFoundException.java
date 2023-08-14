package com.beetech.api_intern.features.products.exceptions;

import com.beetech.api_intern.common.exceptions.EntityNotFoundException;

/**
 * The type Product not found exception.
 */
public class ProductNotFoundException extends EntityNotFoundException {
    private static ProductNotFoundException instance = null;

    /**
     * Instantiates a new Product not found exception.
     *
     * @param message the message
     */
    public ProductNotFoundException(String message) {
        super(message);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ProductNotFoundException getInstance() {
        if (instance == null) {
            instance = new ProductNotFoundException("Product not found!");
        }
        return instance;
    }
}
