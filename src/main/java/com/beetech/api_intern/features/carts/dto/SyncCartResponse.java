package com.beetech.api_intern.features.carts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SyncCartResponse {
    private Long totalQuantity;
}
