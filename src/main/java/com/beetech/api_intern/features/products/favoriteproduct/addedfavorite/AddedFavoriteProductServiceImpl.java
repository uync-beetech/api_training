package com.beetech.api_intern.features.products.favoriteproduct.addedfavorite;

import com.beetech.api_intern.features.products.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddedFavoriteProductServiceImpl implements AddedFavoriteProductService{

    private final AddedFavoriteProductRepository addedFavoriteProductRepository;

    @Override
    public void updateCount(Product product) {
        Optional<AddedFavoriteProduct> optionalAddedFavoriteProduct = addedFavoriteProductRepository.
    }
}
