package com.beetech.api_intern.features.carts.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Add to cart request.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartRequest {
    /**
     * the token must contain 20 chars
     */
    @Size(min = 20, max = 20, message = "token must be 20 characters")
    private String token;

    /**
     * the productId must be greater than or equal to 1
     */
    @Min(value = 1, message = "productId must be greater than or equal to 1")
    private Long productId;

    /**
     * the quantity must be greater than or equal to 1
     */
    @Min(value = 1, message = "quantity must be greater than or equal to 1")
    private Long quantity;
}
