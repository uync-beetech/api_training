package com.beetech.api_intern.features.products.dto;

import com.beetech.api_intern.features.images.dto.ImageDto;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDto {
    private Long id;
    private String name;
    private String sku;
    private String detailInfo;
    private Double price;
    private List<ImageDto> images;
}
