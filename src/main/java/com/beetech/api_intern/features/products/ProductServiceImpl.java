package com.beetech.api_intern.features.products;

import com.beetech.api_intern.features.categories.CategoryRepository;
import com.beetech.api_intern.features.images.Image;
import com.beetech.api_intern.features.images.ImageService;
import com.beetech.api_intern.features.products.dto.CreateProductDto;
import com.beetech.api_intern.features.products.dto.FindAllDto;
import com.beetech.api_intern.features.products.exceptions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Product service.
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;

    @Override
    public Page<Product> findAll(FindAllDto dto, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByCategoryIdAndSearchKey(dto.getCategoryId(), dto.getSearchKey(), pageable);
    }

    @Override
    public Product findOne(String sku) {
        return productRepository.findBySkuAndDeletedIsFalse(sku).orElseThrow(ProductNotFoundException::getInstance);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createProduct(CreateProductDto dto) {
        Product product = Product.builder()
                .category(categoryRepository.findById(dto.getCategoryId()).orElseThrow())
                .name(dto.getName())
                .detailInfo(dto.getDetailInfo())
                .price(dto.getPrice())
                .sku(dto.getSku())
                .build();

        productRepository.save(product);
        ArrayList<Image> images = imageService.saveListImage(dto.getDetailImage());
        Image thumbnail = imageService.saveThumbnail(dto.getThumbnail());
        images.add(thumbnail);
        product.setImages(images);

        productRepository.save(product);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteById(Long id) {
        Product product = productRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(ProductNotFoundException::getInstance);
        product.setOldSku(product.getSku());
        product.setSku(null);
        product.setDeleted(true);
        List<Image> images = product.getImages();
        product.setImages(new ArrayList<>());
        productRepository.save(product);

        imageService.deleteImages(images);
    }
}
