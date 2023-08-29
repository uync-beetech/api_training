package com.beetech.api_intern.features.productstatistic.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProductViewRepository extends JpaRepository<ProductView, ProductViewKey> {

    @Modifying
    @Transactional
    @Query(value = "insert into product_view (product_id, date_view, view_count) values (:product_id, :date_view, :view_count)", nativeQuery = true)
    void createProductView(@Param("product_id") Long productId, @Param("date_view") String dateView, @Param("view_count") Long viewCount);

    @Query("SELECT pv FROM ProductView pv where pv.id.productId = :productId " +
            "order by pv.id.dateView")
    Optional<ProductView> findTopByProductIdOrderByDateDesc(@Param("productId") Long productId);

    @Modifying
    @Transactional
    @Query(value = "update product_view SET  view_count = view_count + 1 where product_id = :productId and date_view = :dateView", nativeQuery = true)
    int updateViewByProductIdAndDate(@Param("productId") Long productId, @Param("dateView") String date);

}
