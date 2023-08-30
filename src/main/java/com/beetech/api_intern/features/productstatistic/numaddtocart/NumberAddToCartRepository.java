package com.beetech.api_intern.features.productstatistic.numaddtocart;

import com.beetech.api_intern.features.productstatistic.ProductStatisticKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The interface Number add to cart repository.
 */
@Repository
public interface NumberAddToCartRepository extends JpaRepository<NumberAddToCart, ProductStatisticKey> {
    /**
     * Create.
     *
     * @param productId  the product id
     * @param dateView   the date view
     * @param addedCount the added count
     */
    @Modifying
    @Transactional
    @Query(value = "insert into " +
            "number_add_to_cart(product_id, update_date, add_to_cart_count) " +
            "values (:product_id, :update_date, :add_to_cart_count)",
            nativeQuery = true)
    void create(@Param("product_id") Long productId, @Param("update_date") String dateView, @Param("add_to_cart_count") Long addedCount);

    /**
     * Find top by product id order by date desc optional.
     *
     * @param productId the product id
     * @return the optional
     */
    @Query("SELECT natc FROM NumberAddToCart natc where natc.id.productId = :productId " +
            "order by natc.id.updateDate desc limit 1")
    Optional<NumberAddToCart> findLast(@Param("productId") Long productId);

    /**
     * Update int.
     *
     * @param productId the product id
     * @param date      the date
     * @return the int
     */
    @Modifying
    @Transactional
    @Query(value = "update number_add_to_cart " +
            "SET add_to_cart_count = add_to_cart_count + 1 " +
            "where product_id = :productId and update_date= :update_date",
            nativeQuery = true)
    int update(@Param("productId") Long productId, @Param("update_date") String date);
}