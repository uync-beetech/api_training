package com.beetech.api_intern.features.carts.dto;

import lombok.Data;

@Data
public class AddToCartResponse {
    private String token;
    private Long totalQuantity;
}
