package com.beetech.api_intern.features.categories.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryRequest {
    @Size(max = 255, message = "name must be less than or equal to 255 characters")
    @NotNull(message = "name must be not null")
    @NotBlank(message = "name must be not blank")
    private String name;
    private List<MultipartFile> image;
}
