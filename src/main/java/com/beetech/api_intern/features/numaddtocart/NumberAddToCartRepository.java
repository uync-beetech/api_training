package com.beetech.api_intern.features.numaddtocart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NumberAddToCartRepository extends JpaRepository<NumberAddToCart, Long> {
    Optional<NumberAddToCart> findByProductId(Long productId);
}