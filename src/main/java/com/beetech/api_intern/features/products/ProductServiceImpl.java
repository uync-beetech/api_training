package com.beetech.api_intern.features.products;

import com.beetech.api_intern.features.products.dto.FindAllDto;
import com.beetech.api_intern.features.products.exceptions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Page<Product> findAll(FindAllDto dto, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByCategoryIdAndSearchKey(dto.getCategoryId(), dto.getSearchKey(), pageable);
    }

    @Override
    public Product findOne(String sku) {
        return productRepository.findBySkuAndDeletedIsFalse(sku).orElseThrow(ProductNotFoundException::getInstance);
    }
}
