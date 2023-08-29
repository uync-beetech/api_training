package com.beetech.api_intern.features.products.favoriteproduct.removedfavorite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemovedFavoriteProductRepository extends JpaRepository<RemovedFavoriteProduct, Long> {
}