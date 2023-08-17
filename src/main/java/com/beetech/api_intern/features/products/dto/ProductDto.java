package com.beetech.api_intern.features.products.dto;

import com.beetech.api_intern.features.images.dto.ImageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String detailInfo;
    private Double price;
    private List<ImageDto> images;
}
