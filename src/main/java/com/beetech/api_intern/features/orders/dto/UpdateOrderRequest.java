package com.beetech.api_intern.features.orders.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateOrderRequest {
    private Long id;
    @NotBlank
    @NotNull
    private String displayId;
    @NotNull
    private Integer status;
}
