package com.beetech.api_intern.features.products.productstatistic.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductViewRepository extends JpaRepository<ProductView, Long> {
    Optional<ProductView> findTopByProductIdOrderByIdDesc(Long productId);
}
