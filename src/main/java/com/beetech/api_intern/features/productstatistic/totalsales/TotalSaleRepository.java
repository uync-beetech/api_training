package com.beetech.api_intern.features.productstatistic.totalsales;

import com.beetech.api_intern.features.productstatistic.ProductStatisticKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The interface Total sale repository.
 */
@Repository
public interface TotalSaleRepository extends JpaRepository<TotalSale, ProductStatisticKey> {
    /**
     * Create.
     *
     * @param productId  the product id
     * @param updateDate the date view
     * @param saleCount  the sale count
     */
    @Modifying
    @Transactional
    @Query(value = "insert into " +
            "total_sale(product_id, update_date, sale_count) " +
            "values (:product_id, :update_date, :sale_count)",
            nativeQuery = true)
    void create(@Param("product_id") Long productId, @Param("update_date") String updateDate, @Param("sale_count") Long saleCount);

    /**
     * Find top by product id order by date desc optional.
     *
     * @param productId the product id
     * @return the optional
     */
    @Query("SELECT ts FROM TotalSale ts where ts.id.productId = :productId " +
            "order by ts.id.updateDate desc limit 1")
    Optional<TotalSale> findLast(@Param("productId") Long productId);

    /**
     * Update int.
     *
     * @param productId  the product id
     * @param updateDate the update date
     * @return the int
     */
    @Modifying
    @Transactional
    @Query(value = "update total_sale " +
            "SET sale_count= sale_count+ :num_additions " +
            "where product_id = :productId and update_date= :update_date",
            nativeQuery = true)
    int update(@Param("productId") Long productId, @Param("update_date") String updateDate, @Param("num_additions") Long numberOfAdditions);
}