package com.beetech.api_intern.features.products.productstatistic.favoriteproduct.addedfavorite;

import com.beetech.api_intern.features.products.productstatistic.ProductStatisticKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The interface Added favorite product repository.
 */
@Repository
public interface AddedFavoriteProductRepository extends JpaRepository<AddedFavoriteProduct, ProductStatisticKey> {
    /**
     * Create.
     *
     * @param productId  the product id
     * @param addedCount the added count
     * @param dateView   the date view
     */
    @Modifying
    @Transactional
    @Query(value = "insert into " +
            "added_favorite_product(product_id, update_date, added_count) " +
            "values (:product_id, :update_date, :added_count)",
            nativeQuery = true)
    void create(@Param("product_id") Long productId, @Param("update_date") String dateView, @Param("added_count") Long addedCount);

    /**
     * Find top by product id order by date desc optional.
     *
     * @param productId the product id
     * @return the optional
     */
    @Query("SELECT afp FROM AddedFavoriteProduct afp where afp.id.productId = :productId " +
            "order by afp.id.updateDate desc limit 1")
    Optional<AddedFavoriteProduct> findLast(@Param("productId") Long productId);

    /**
     * Update int.
     *
     * @param productId the product id
     * @param date      the date
     * @return the int
     */
    @Modifying
    @Transactional
    @Query(value = "update added_favorite_product " +
            "SET  added_count = added_count + 1 " +
            "where product_id = :productId and update_date= :update_date",
            nativeQuery = true)
    int update(@Param("productId") Long productId, @Param("update_date") String date);
}