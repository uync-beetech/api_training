package com.beetech.api_intern.features.carts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartDto {
    private String token;
    private String userNote;
    private Long productId;
    private Long quantity;
}
