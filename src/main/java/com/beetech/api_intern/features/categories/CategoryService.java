package com.beetech.api_intern.features.categories;

import com.beetech.api_intern.features.categories.dto.CreateCategoryRequest;
import com.beetech.api_intern.features.categories.dto.UpdateCategoryRequest;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    void createCategory(CreateCategoryRequest createCategoryRequest);
    void updateCategory(Long id, UpdateCategoryRequest updateCategoryRequest);
    void deleteById(Long categoryId);
}
