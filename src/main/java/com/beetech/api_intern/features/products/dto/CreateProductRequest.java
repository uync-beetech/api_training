package com.beetech.api_intern.features.products.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class CreateProductRequest {
    @NotNull(message = "sku is required")
    @Size(max = 50, message = "sku must be less than or equal to 50 characters")
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "sku is invalid")
    String sku;

    @NotNull(message = "name is required")
    @Size(max = 255, message = "name must be less than or equal to 255 characters")
    String name;

    @NotNull
    @Size(max = 1000, message = "name must be less than or equal to 1000 characters")
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
