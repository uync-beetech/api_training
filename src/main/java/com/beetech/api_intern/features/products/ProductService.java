package com.beetech.api_intern.features.products;

import com.beetech.api_intern.features.products.dto.FindAllDto;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<Product> findAll(FindAllDto dto, Integer size, Integer page);
    Product findOne(String sku);
}
