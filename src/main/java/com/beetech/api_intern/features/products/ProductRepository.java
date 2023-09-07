package com.beetech.api_intern.features.products;

import com.beetech.api_intern.features.products.productstatistic.dto.ProductStatisticsInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p from Product p where p.category.id = :categoryId and ((p.name like %:searchKey%) or (p.sku like %:searchKey%)) and (p.deleted = false)")
    Page<Product> findByCategoryIdAndSearchKey(@Param("categoryId") Long categoryId, @Param("searchKey") String searchKey, Pageable pageable);

    Optional<Product> findBySkuAndDeletedIsFalse(String sku);

    Optional<Product> findByIdAndDeletedIsFalse(Long id);

    Boolean existsBySku(String sku);

    @Query(value = "SELECT " +
            "p.id as id, " +
            "p.sku as sku, " +
            "p.name as name, " +
            "COALESCE(pvGroup.view, 0) as view, " +
            "COALESCE(afpGroup.added_count, 0) as addedFavorite, " +
            "COALESCE(rfpGroup.removed_count, 0) as removedFavorite, " +
            "COALESCE(natcGroup.add_to_cart_count, 0) as numberAddToCarts, " +
            "COALESCE(ttGroup.transaction_count, 0) as totalTransactions, " +
            "COALESCE(tsGroup.sale_count, 0) as totalSales, " +
            "IF(" +
            "   pvGroup.view > 0 AND ttGroup.transaction_count > 0, " +
            "   CAST((ttGroup.transaction_count) as DOUBLE) " +
            "       / CAST((pvGroup.view) as DOUBLE), " +
            "   0" +
            ") AS transactionViewRatio " +
            "FROM product p " +

            // join product view
            "LEFT JOIN ( " +
            "   SELECT pv.product_id, " +
            "       MAX(IF(pv.update_date <= :end_date, pv.view_count, 0)) - " +
            "       MAX(IF(pv.update_date < :start_date, pv.view_count, 0)) as view " +
            "   FROM product_view pv " +
            "   WHERE pv.update_date <= :end_date " +
            "   GROUP BY pv.product_id" +
            ") AS pvGroup ON p.id = pvGroup.product_id " +

            // left join added favorite product
            "LEFT JOIN (" +
            "   SELECT " +
            "       afp.product_id," +
            "       MAX(IF(afp.update_date <= :end_date, afp.added_count, 0)) - " +
            "       MAX(IF(afp.update_date < :start_date, afp.added_count, 0)) as added_count " +
            "   FROM added_favorite_product afp " +
            "   WHERE afp.update_date <= :end_date " +
            "   GROUP BY afp.product_id " +
            ") AS afpGroup ON afpGroup.product_id = p.id " +

            // left join removed favorite product
            "LEFT JOIN (" +
            "   SELECT " +
            "       rfp.product_id, " +
            "       MAX(IF(rfp.update_date <= :end_date, rfp.removed_count, 0)) - " +
            "       MAX(IF(rfp.update_date < :start_date, rfp.removed_count, 0)) as removed_count " +
            "   FROM removed_favorite_product rfp " +
            "   WHERE rfp.update_date <= :end_date " +
            "   GROUP BY rfp.product_id " +
            ") AS rfpGroup on rfpGroup.product_id = p.id " +

            // left join number add to cart
            "LEFT JOIN (" +
            "   SELECT " +
            "       natc.product_id, " +
            "       MAX(IF(natc.update_date <= :end_date, natc.add_to_cart_count, 0)) - " +
            "       MAX(IF(natc.update_date < :start_date, natc.add_to_cart_count, 0)) as add_to_cart_count " +
            "   FROM number_add_to_cart natc " +
            "   WHERE natc.update_date <= :end_date " +
            "   GROUP BY natc.product_id" +
            ") as natcGroup on natcGroup.product_id = p.id " +

            // left join total transaction
            "LEFT JOIN (" +
            "   SELECT " +
            "       tt.product_id," +
            "       MAX(IF(tt.update_date <= :end_date, tt.transactions, 0)) - " +
            "       MAX(IF(tt.update_date < :start_date, tt.transactions, 0)) as transaction_count " +
            "   FROM total_transaction tt " +
            "   WHERE tt.update_date <= :end_date " +
            "   GROUP BY tt.product_id " +
            ") AS ttGroup ON ttGroup.product_id = p.id " +

            // left join total sales
            "LEFT JOIN (" +
            "   SELECT " +
            "       ts.product_id," +
            "       MAX(IF(ts.update_date <= :end_date, ts.sale_count, 0)) - " +
            "       MAX(IF(ts.update_date < :start_date, ts.sale_count, 0)) as sale_count " +
            "   FROM total_sale ts " +
            "   WHERE ts.update_date <= :end_date " +
            "   GROUP BY ts.product_id" +
            ") AS tsGroup ON tsGroup.product_id = p.id " +

            // if the product has not been deleted.
            "WHERE p.delete_flag = 0",
            nativeQuery = true
    )
    Page<ProductStatisticsInterface> findProductStatistics(@Param("start_date") String startDate, @Param("end_date") String endDate, Pageable pageable);
}
