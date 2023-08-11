package com.beetech.api_intern.features.categories;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    void deleteById(Long categoryId);
}
