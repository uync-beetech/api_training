package com.beetech.api_intern.features.orders.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindAllOrderRequest {
    private String userName;

    private Long orderId;

    private String sku;

    private String productName;

    private Integer status;

    @Pattern(regexp = "^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[1-2]\\d|3[0-1])$", message = "Invalid date format")
    private String orderDate;
}
