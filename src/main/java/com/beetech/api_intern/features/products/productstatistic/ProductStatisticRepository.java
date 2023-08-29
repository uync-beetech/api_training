package com.beetech.api_intern.features.products.productstatistic;

import com.beetech.api_intern.features.products.productstatistic.dto.ProductStatisticsInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductStatisticRepository extends JpaRepository<ProductStatistic, Long> {
    Optional<ProductStatistic> findByProductId(Long productId);
    @Query("SELECT p.id as id, p.sku as sku, p.name as name, " +
            "COALESCE(ps.numberAddToCarts, 0) AS numberAddToCarts, " +
            "COALESCE(ps.totalTransactions, 0) AS totalTransactions, " +
            "COALESCE(ps.totalSales, 0) AS totalSales, " +
            "CASE WHEN ps.view > 0 THEN COALESCE(cast(ps.totalTransactions as DOUBLE) , 0) / ps.view ELSE 0 END AS transactionViewRatio " +
            "FROM Product p " +
            "LEFT JOIN FavoriteProduct fp ON p.id = fp.product.id " +
            "LEFT JOIN ProductStatistic ps ON p.id = ps.product.id")
    Page<ProductStatisticsInterface> analyzeAllProductStatistic(Pageable pageable);
}