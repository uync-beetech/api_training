package com.beetech.api_intern.features.products.productstatistic;

import com.beetech.api_intern.features.products.productstatistic.dto.ProductStatisticsInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductStatisticServiceImpl implements ProductStatisticService {
    private final ProductStatisticRepository productStatisticRepository;


    @Override
    public Page<ProductStatisticsInterface> findAll(Integer page, Integer size) {
        // create pageable for pagination
        Pageable pageable = PageRequest.of(page, size);
        // query database
        return productStatisticRepository.analyzeAllProductStatistic(pageable);
    }
}
