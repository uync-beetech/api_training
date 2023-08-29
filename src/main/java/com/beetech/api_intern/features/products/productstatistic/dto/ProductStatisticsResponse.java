package com.beetech.api_intern.features.products.productstatistic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStatisticsResponse {
    List<ProductStatisticsInterface> productStatistics;
    private Integer pageNumber;
    private Integer totalPages;
}
