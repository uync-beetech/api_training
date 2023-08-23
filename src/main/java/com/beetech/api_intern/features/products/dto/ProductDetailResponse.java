package com.beetech.api_intern.features.products.dto;

import com.beetech.api_intern.features.images.dto.ImageResponse;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailResponse {
    private Long id;
    private String name;
    private String sku;
    private String detailInfo;
    private Double price;
    private List<ImageResponse> images;
}
