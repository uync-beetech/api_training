package com.beetech.api_intern.features.carts.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartRequest {
    @Size(min = 20, max = 20, message = "token must be 20 characters")
    private String token;
    @Min(value = 1, message = "productId must be greater than 0")
    private Long productId;
    @Min(value = 1, message = "quantity must be greater than 0")
    private Long quantity;
}
