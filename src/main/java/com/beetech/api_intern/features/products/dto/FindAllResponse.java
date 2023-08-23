package com.beetech.api_intern.features.products.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindAllResponse {
    private List<ProductResponse> products;
    private Integer totalPages;
    private Integer pageNumber;
}
