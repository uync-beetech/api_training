package com.beetech.api_intern.features.categories;

import com.beetech.api_intern.features.categories.dto.CategoryResponse;
import com.beetech.api_intern.features.categories.dto.CreateCategoryRequest;
import com.beetech.api_intern.features.categories.dto.UpdateCategoryRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @GetMapping("categories")
    public ResponseEntity<List<CategoryResponse>> findAll() {
        List<CategoryResponse> categories = categoryService.findAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .toList();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("add-category")
    public ResponseEntity<Object> createCategory(@Valid @ModelAttribute CreateCategoryRequest categoryRequest) {
        categoryService.createCategory(categoryRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("update-category/{id}")
    public ResponseEntity<Object> updateCategory(@Valid @ModelAttribute UpdateCategoryRequest categoryRequest, @PathVariable Long id) {
        categoryService.updateCategory(id, categoryRequest);
        return ResponseEntity.ok().build();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @DeleteMapping("delete-category/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
