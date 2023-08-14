package com.beetech.api_intern.features.carts.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddToCartResponse {
    private String token;
    private Long totalQuantity;
}
