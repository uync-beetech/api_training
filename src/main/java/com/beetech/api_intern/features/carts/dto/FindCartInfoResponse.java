package com.beetech.api_intern.features.carts.dto;

import com.beetech.api_intern.features.carts.cartdetails.CartDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindCartInfoResponse {
    private Long id;
    private Double totalPrice;
    private List<CartDetailResponse> details;
}
