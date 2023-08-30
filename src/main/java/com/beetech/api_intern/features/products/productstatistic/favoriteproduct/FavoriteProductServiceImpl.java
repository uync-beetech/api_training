package com.beetech.api_intern.features.products.productstatistic.favoriteproduct;

import com.beetech.api_intern.common.utils.UserUtils;
import com.beetech.api_intern.features.products.Product;
import com.beetech.api_intern.features.products.ProductRepository;
import com.beetech.api_intern.features.products.exceptions.ProductNotFoundException;
import com.beetech.api_intern.features.products.productstatistic.favoriteproduct.addedfavorite.AddedFavoriteProductService;
import com.beetech.api_intern.features.products.productstatistic.favoriteproduct.removedfavorite.RemovedFavoriteProductService;
import com.beetech.api_intern.features.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteProductServiceImpl implements FavoriteProductService {
    private final FavoriteProductRepository favoriteProductRepository;
    private final ProductRepository productRepository;
    private final AddedFavoriteProductService addedFavoriteProductService;
    private final RemovedFavoriteProductService removedFavoriteProductService;

    @Override
    @Transactional
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
            addedFavoriteProductService.updateCount(product);
            return;
        }

        FavoriteProduct favoriteProduct = optionalFavoriteProduct.get();
        if (!favoriteProduct.isAdded()) {
            // update added status is false
            favoriteProduct.setAdded(true);
            // plus added count
            addedFavoriteProductService.updateCount(product);
            favoriteProductRepository.save(favoriteProduct);
        }
    }

    @Override
    @Transactional
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
            removedFavoriteProductService.updateCount(product);
            // save to database
            favoriteProductRepository.save(favoriteProduct);
        }
    }
}
