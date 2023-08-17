package com.beetech.api_intern.features.carts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FindTotalQuantityResponse {
    private Long totalQuantity;
    private Long versionNo;
}
