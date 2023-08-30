package com.beetech.api_intern.features.productstatistic.view;

import com.beetech.api_intern.features.productstatistic.ProductStatisticKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProductViewRepository extends JpaRepository<ProductView, ProductStatisticKey> {

    @Modifying
    @Transactional
    @Query(value = "insert into " +
            "product_view (product_id, update_date, view_count, updated) " +
            "values (:product_id, :update_date, :view_count, CURRENT_TIMESTAMP)",
            nativeQuery = true)
    void createProductView(@Param("product_id") Long productId, @Param("view_count") Long viewCount, @Param("update_date") String dateView);

    @Query("SELECT pv FROM ProductView pv where pv.id.productId = :productId " +
            "order by pv.id.updateDate desc limit 1")
    Optional<ProductView> findTopByProductIdOrderByDateDesc(@Param("productId") Long productId);

    @Modifying
    @Transactional
    @Query(value = "update product_view SET  view_count = view_count + 1, updated = CURRENT_TIMESTAMP where product_id = :productId and update_date= :update_date", nativeQuery = true)
    int updateViewByProductIdAndDate(@Param("productId") Long productId, @Param("update_date") String date);

}
