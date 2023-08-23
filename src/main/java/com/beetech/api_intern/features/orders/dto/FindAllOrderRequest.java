package com.beetech.api_intern.features.orders.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindAllOrderRequest {
    private String username;

    private Long orderId;

    private String sku;

    private String productName;

    private Integer status;

    @Pattern(regexp = "\\b(?:0?[1-9]|[12][0-9]|3[01])/(?:0?[1-9]|1[0-2])/[0-9]{4}\\b", message = "Invalid date format")
    private String orderDate;
}
