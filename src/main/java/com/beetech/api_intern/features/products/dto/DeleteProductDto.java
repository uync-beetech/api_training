package com.beetech.api_intern.features.products.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteProductDto {
    @Min(value = 1, message = "id must be greater than or equal to 1")
    private Long id;
}
