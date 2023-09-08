package com.beetech.api_intern.features.categories;

import com.beetech.api_intern.common.responses.CommonResponseBody;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Category controller.
 */
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping("categories")
    public ResponseEntity<CommonResponseBody<Object>> findAll() {
        // find all categories
        List<CategoryResponse> categories = categoryService.findAll()
                .stream()
                // map to response data format
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .toList();
        // create map data
        Map<String, Object> data = new HashMap<>();
        // put categories to data
        data.put("categories", categories);
        // return response to client
        return ResponseEntity.ok(new CommonResponseBody<>(data));
    }

    /**
     * Create category response entity.
     *
     * @param categoryRequest the category request
     * @return the response entity
     */
    @PostMapping("add-category")
    public ResponseEntity<CommonResponseBody<Object>> createCategory(@Valid @ModelAttribute CreateCategoryRequest categoryRequest) {
        categoryService.createCategory(categoryRequest);
        return ResponseEntity.ok(new CommonResponseBody<>());
    }

    /**
     * Update category response entity.
     *
     * @param categoryRequest the category request
     * @param id              the id
     * @return the response entity
     */
    @PostMapping("update-category/{id}")
    public ResponseEntity<CommonResponseBody<Object>> updateCategory(@Valid @ModelAttribute UpdateCategoryRequest categoryRequest, @PathVariable Long id) {
        categoryService.updateCategory(id, categoryRequest);
        return ResponseEntity.ok(new CommonResponseBody<>());
    }

    /**
     * Delete by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @DeleteMapping("delete-category/{id}")
    public ResponseEntity<CommonResponseBody<Object>> deleteById(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.ok(new CommonResponseBody<>());
    }

}
