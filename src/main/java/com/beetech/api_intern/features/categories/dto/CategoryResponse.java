package com.beetech.api_intern.features.categories.dto;

import com.beetech.api_intern.features.images.dto.ImageResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    private Set<ImageResponse> images;
}
