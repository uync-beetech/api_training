package com.beetech.api_intern.features.products.productstatistic;

import com.beetech.api_intern.features.products.productstatistic.dto.ProductStatisticsInterface;
import com.beetech.api_intern.features.products.productstatistic.dto.ProductStatisticsRequest;
import org.springframework.data.domain.Page;

public interface ProductStatisticService {
    Page<ProductStatisticsInterface> findAll(ProductStatisticsRequest request, Integer page, Integer size);
}
