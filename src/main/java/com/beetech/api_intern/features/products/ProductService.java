package com.beetech.api_intern.features.products;

import com.beetech.api_intern.features.products.dto.CreateProductRequest;
import com.beetech.api_intern.features.products.dto.FindAllRequest;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<Product> findAll(FindAllRequest dto, Integer size, Integer page);
    Product findOne(String sku);
    void createProduct(CreateProductRequest dto);
    void deleteById(Long id);
}
