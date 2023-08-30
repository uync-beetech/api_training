package com.beetech.api_intern.features.productstatistic.favoriteproduct.removedfavorite;

import com.beetech.api_intern.features.productstatistic.ProductStatisticKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RemovedFavoriteProductRepository
        extends JpaRepository<RemovedFavoriteProduct, ProductStatisticKey> {
    @Modifying
    @Transactional
    @Query(value = "insert into " +
            "removed_favorite_product(product_id, update_date, removed_count) " +
            "values (:product_id, :update_date, :removed_count)",
            nativeQuery = true)
    void create(@Param("product_id") Long productId, @Param("update_date") String dateView, @Param("removed_count") Long removedCount);

    @Query("SELECT rfp FROM RemovedFavoriteProduct rfp where rfp.id.productId = :productId " +
            "order by rfp.id.updateDate desc limit 1")
    Optional<RemovedFavoriteProduct> findLast(@Param("productId") Long productId);

    @Modifying
    @Transactional
    @Query(value = "update removed_favorite_product " +
            "SET  removed_count= removed_count + 1 " +
            "where product_id = :productId and update_date= :update_date",
            nativeQuery = true)
    int update(@Param("productId") Long productId, @Param("update_date") String date);
}