package com.beetech.api_intern.features.productstatistic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductStatisticRepository extends JpaRepository<ProductStatistic, Long> {
    Optional<ProductStatistic> findByProductId(Long productId);
}