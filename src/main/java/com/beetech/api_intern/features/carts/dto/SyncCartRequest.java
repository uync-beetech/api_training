package com.beetech.api_intern.features.carts.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SyncCartRequest {
    @Size(min = 20, max = 20, message = "token must contain 20 characters")
    private String token;
}
