package com.beetech.api_intern.features.categories.dto;

import com.beetech.api_intern.features.images.dto.ImageDto;
import lombok.Data;

import java.util.Set;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private Set<ImageDto> images;
}
