package com.beetech.api_intern.features.products.productstatistic.totaltransactions;

import com.beetech.api_intern.features.products.productstatistic.ProductStatisticKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The interface Total transaction's repository.
 */
@Repository
public interface TotalTransactionRepository extends JpaRepository<TotalTransaction, ProductStatisticKey> {
    /**
     * Create.
     *
     * @param productId    the product id
     * @param updateDate   the update date
     * @param transactions the transactions
     */
    @Modifying
    @Transactional
    @Query(value = "insert into " +
            "total_transaction(product_id, update_date, transactions) " +
            "values (:product_id, :update_date, :transactions)",
            nativeQuery = true)
    void create(@Param("product_id") Long productId, @Param("update_date") String updateDate, @Param("transactions") Long transactions);

    /**
     * Find top by product id order by date desc optional.
     *
     * @param productId the product id
     * @return the optional
     */
    @Query("SELECT tt FROM TotalTransaction tt where tt.id.productId = :productId " +
            "order by tt.id.updateDate desc limit 1")
    Optional<TotalTransaction> findLast(@Param("productId") Long productId);

    /**
     * Update int.
     *
     * @param productId  the product id
     * @param updateDate the update date
     * @return the int
     */
    @Modifying
    @Transactional
    @Query(value = "update total_transaction " +
            "SET transactions = transactions + 1 " +
            "where product_id = :productId and update_date= :update_date",
            nativeQuery = true)
    int update(@Param("productId") Long productId, @Param("update_date") String updateDate);
}