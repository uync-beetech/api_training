package com.beetech.api_intern.features.products.dto;

import com.beetech.api_intern.features.images.dto.ImageResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String detailInfo;
    private Double price;
    private List<ImageResponse> images;
}
