package com.beetech.api_intern.features.products.productstatistic;

import com.beetech.api_intern.features.products.productstatistic.dto.ProductStatisticsInterface;
import org.springframework.data.domain.Page;

public interface ProductStatisticService {
    Page<ProductStatisticsInterface> findAll(Integer page, Integer size);
}
