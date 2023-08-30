package com.beetech.api_intern.features.products.productstatistic.favoriteproduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Long> {
    Optional<FavoriteProduct> findByUserIdAndProductId(Long userId, Long productId);
}
