package com.beetech.api_intern.features.products;

import com.beetech.api_intern.common.exceptions.BadRequestException;
import com.beetech.api_intern.features.categories.CategoryRepository;
import com.beetech.api_intern.features.images.Image;
import com.beetech.api_intern.features.images.ImageService;
import com.beetech.api_intern.features.products.dto.CreateProductRequest;
import com.beetech.api_intern.features.products.dto.FindAllRequest;
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
    public Page<Product> findAll(FindAllRequest dto, Integer size, Integer page) {
        // create pageable for pagination
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByCategoryIdAndSearchKey(dto.getCategoryId(), dto.getSearchKey(), pageable);
    }

    @Override
    public Product findOne(String sku) {
        return productRepository.findBySkuAndDeletedIsFalse(sku).orElseThrow(ProductNotFoundException::getInstance);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createProduct(CreateProductRequest dto) {
        if (productRepository.existsBySku(dto.getSku())) {
            throw new BadRequestException("sku already exist");
        }
        // create product from request data
        Product product = Product.builder()
                .category(categoryRepository.findById(dto.getCategoryId()).orElseThrow())
                .name(dto.getName())
                .detailInfo(dto.getDetailInfo())
                .price(dto.getPrice())
                .sku(dto.getSku())
                .build();
        productRepository.save(product);

        // create and save detail images of product
        ArrayList<Image> images = imageService.saveListImage(dto.getDetailImage(), product.getSku());
        // create and save thumbnail image
        Image thumbnail = imageService.saveThumbnail(dto.getThumbnail(), product.getSku());
        // add thumbnail to list image
        images.add(thumbnail);
        // set saved images for product
        product.setImages(images);

        // save product to database
        productRepository.save(product);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteById(Long id) {
        // find product by id
        Product product = productRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(ProductNotFoundException::getInstance);
        // backup old sku
        product.setOldSku(product.getSku());
        // remove sku
        product.setSku(null);
        // set deleted = true
        product.setDeleted(true);
        // get all product image
        List<Image> images = product.getImages();
        // clear product image
        product.setImages(new ArrayList<>());
        // save product to db
        productRepository.save(product);

        // delete product images
        imageService.deleteImages(images, product.getOldSku());
    }
}
