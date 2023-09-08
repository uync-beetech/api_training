package com.beetech.api_intern.features.carts.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindTotalQuantityRequest {
    /**
     * validate token: contain 20 chars
     */
    @Size(min = 20, max = 20, message = "token must contain 20 characters")
    private String token;
}
