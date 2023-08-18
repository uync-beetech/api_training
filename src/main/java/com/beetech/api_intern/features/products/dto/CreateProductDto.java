package com.beetech.api_intern.features.products.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto {
    @NotNull(message = "sku is required")
    String sku;

    @NotNull(message = "name is required")
    String name;

    @NotNull
    String detailInfo;

    @NotNull(message = "categoryId is required")
    @Min(value = 1L, message = "categoryId must be greater than or equal to 1")
    Long categoryId;

    @NotNull
    Double price;

    @NotNull
    List<MultipartFile> detailImage;
    @NotNull
    MultipartFile thumbnail;
}
