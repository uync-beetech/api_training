package com.beetech.api_intern.features.products.favoriteproduct.addedfavorite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddedFavoriteProductRepository extends JpaRepository<AddedFavoriteProduct, Long> {
    Optional<AddedFavoriteProduct> findTopByProductIdOrderByIdDesc(Long productId);
}