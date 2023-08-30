package com.beetech.api_intern.features.products.productstatistic;

import com.beetech.api_intern.features.products.productstatistic.dto.ProductStatisticsInterface;
import com.beetech.api_intern.features.products.productstatistic.dto.ProductStatisticsRequest;
import com.beetech.api_intern.features.products.productstatistic.dto.ProductStatisticsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductStatisticController {
    private final ProductStatisticService productStatisticService;

    @PostMapping("/product-statistics")
    public ResponseEntity<ProductStatisticsResponse> analyzeProductStatistic(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @Valid @RequestBody ProductStatisticsRequest request
    ) {
        // get page data from service
        Page<ProductStatisticsInterface> statisticsPage = productStatisticService.findAll(request, page, size);
        // create response data
        ProductStatisticsResponse data = ProductStatisticsResponse.builder()
                .productStatistics(statisticsPage.getContent())
                .totalPages(statisticsPage.getTotalPages())
                .pageNumber(statisticsPage.getNumber())
                .build();

        return ResponseEntity.ok(data);
    }
}
