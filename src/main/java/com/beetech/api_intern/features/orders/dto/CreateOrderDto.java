package com.beetech.api_intern.features.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDto {
    private String phoneNumber;
    private String address;
    private Integer cityId;
    private Integer districtId;
    private Long versionNo;
}
