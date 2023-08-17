package com.beetech.api_intern.features.products.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FindAllDto {
    private String searchKey;
    private Long categoryId;
}
