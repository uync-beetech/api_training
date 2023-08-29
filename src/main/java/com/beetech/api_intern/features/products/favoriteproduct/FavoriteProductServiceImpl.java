package com.beetech.api_intern.features.products.favoriteproduct;

import com.beetech.api_intern.common.utils.UserUtils;
import com.beetech.api_intern.features.products.Product;
import com.beetech.api_intern.features.products.ProductRepository;
import com.beetech.api_intern.features.products.exceptions.ProductNotFoundException;
import com.beetech.api_intern.features.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteProductServiceImpl implements FavoriteProductService {
    private final FavoriteProductRepository favoriteProductRepository;
    private final ProductRepository productRepository;

    @Override
    public void addFavoriteProduct(String sku) {
        // find product by sku
        Product product = productRepository.findBySkuAndDeletedIsFalse(sku)
                .orElseThrow(ProductNotFoundException::getInstance);
        // get authenticated user
        User user = UserUtils.getUser();
        // check product in user's favorite products
        Optional<FavoriteProduct> optionalFavoriteProduct = favoriteProductRepository.findByUserIdAndProductId(user.getId(), product.getId());
        // If the user hasn't added this product to favorites yet
        if (optionalFavoriteProduct.isEmpty()) {
            // create new favorite product
            FavoriteProduct favoriteProduct = FavoriteProduct.builder()
                    .user(user)
                    .product(product)
                    .build();
            favoriteProductRepository.save(favoriteProduct);
            return;
        }

        FavoriteProduct favoriteProduct = optionalFavoriteProduct.get();
        if (!favoriteProduct.isAdded()) {
            // update added status is false
            favoriteProduct.setAdded(true);
            favoriteProductRepository.save(favoriteProduct);
        }
    }

    @Override
    public void removeFavoriteProduct(String sku) {
        // find product by sku
        Product product = productRepository.findBySkuAndDeletedIsFalse(sku)
                .orElseThrow(ProductNotFoundException::getInstance);
        // get authenticated user
        User user = UserUtils.getUser();
        // find favorite product
        Optional<FavoriteProduct> optionalFavoriteProduct = favoriteProductRepository.findByUserIdAndProductId(user.getId(), product.getId());
        // If the user hasn't added this product to favorites yet, not handle
        if (optionalFavoriteProduct.isEmpty()) {
            return;
        }

        // the user already add this product to favorites yet
        FavoriteProduct favoriteProduct = optionalFavoriteProduct.get();
        // and status is added
        if (favoriteProduct.isAdded()) {
            // set added to false
            favoriteProduct.setAdded(false);
            // plus removedCount
            // save to database
            favoriteProductRepository.save(favoriteProduct);
        }
    }
}
