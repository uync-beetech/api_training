package com.beetech.api_intern.features.carts.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCartRequest {
    private String token;

    private Integer clearCart;

    @NotNull
    private Long id;
}
