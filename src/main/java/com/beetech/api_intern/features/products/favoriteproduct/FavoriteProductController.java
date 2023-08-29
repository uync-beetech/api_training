package com.beetech.api_intern.features.products.favoriteproduct;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FavoriteProductController {
    private final FavoriteProductService favoriteProductService;

    @PostMapping("/add-favorite-product/{sku}")
    public ResponseEntity<Object> addFavoriteProduct(@PathVariable String sku) {
        favoriteProductService.addFavoriteProduct(sku);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove-favorite-product/{sku}")
    public ResponseEntity<Object> removeFavoriteProduct(@PathVariable String sku) {
        favoriteProductService.removeFavoriteProduct(sku);
        return ResponseEntity.ok().build();
    }
}
