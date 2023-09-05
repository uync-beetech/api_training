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
            "COALESCE(pvEnd.view_count, 0) - COALESCE(pvStart.view_count, 0) as view, " +
            "COALESCE(afpEnd.added_count, 0) - COALESCE(afpStart.added_count, 0) as addedFavorite, " +
            "COALESCE(rfpEnd.removed_count, 0) - COALESCE(rfpStart.removed_count, 0) as removedFavorite, " +
            "COALESCE(atcEnd.add_to_cart_count, 0) - COALESCE(atcStart.add_to_cart_count, 0) as numberAddToCarts, " +
            "COALESCE(ttEnd.transactions, 0) - COALESCE(ttStart.transactions, 0) as totalTransactions, " +
            "COALESCE(tsEnd.sale_count, 0) - COALESCE(tsStart.sale_count, 0) as totalSales, " +
            "IF(" +
            "   COALESCE(pvEnd.view_count, 0) - COALESCE(pvStart.view_count, 0) > 0, " +
            "   CAST((COALESCE(ttEnd.transactions, 0) - COALESCE(ttStart.transactions, 0)) as DOUBLE) " +
            "       / CAST(COALESCE(pvEnd.view_count, 0) - COALESCE(pvStart.view_count, 0) as DOUBLE), " +
            "   0" +
            ") AS transactionViewRatio " +
            "FROM product p " +

            // join product view
            "LEFT JOIN ( " +
            "   SELECT pv.product_id, " +
            "       MAX(IF(pv.update_date < :start_date, pv.update_date, NULL)) as maxStartDate, " +
            "       MAX(IF(pv.update_date <= :end_date, pv.update_date, NULL)) as maxEndDate " +
            "   FROM product_view pv " +
            "   WHERE pv.update_date <= :end_date " +
            "   GROUP BY pv.product_id" +
            ") AS pvGroup ON p.id = pvGroup.product_id " +
            "LEFT JOIN product_view pvStart " +
            "   ON pvStart.product_id = p.id AND pvStart.update_date = pvGroup.maxStartDate " +
            "LEFT JOIN product_view pvEnd " +
            "   ON pvEnd.product_id = p.id and pvEnd.update_date = pvGroup.maxEndDate " +

            // left join added favorite product
            "LEFT JOIN (" +
            "   SELECT " +
            "       afp.product_id," +
            "       MAX(IF(afp.update_date < :start_date, afp.update_date, NULL)) as maxStartDate, " +
            "       MAX(IF(afp.update_date <= :end_date, afp.update_date, NULL)) as maxEndDate " +
            "   FROM added_favorite_product afp " +
            "   WHERE afp.update_date <= :end_date " +
            "   GROUP BY afp.product_id " +
            ") AS afpGroup ON afpGroup.product_id = p.id " +
            "LEFT JOIN added_favorite_product afpStart " +
            "   ON afpStart.product_id = p.id AND afpStart.update_date = afpGroup.maxStartDate " +
            "LEFT JOIN added_favorite_product afpEnd " +
            "   ON afpEnd.product_id = p.id AND afpEnd.update_date = afpGroup.maxEndDate " +

            // left join removed favorite product
            "LEFT JOIN (" +
            "   SELECT " +
            "       rfp.product_id, " +
            "       MAX(IF(rfp.update_date < :start_date, rfp.update_date, NULL)) as maxStartDate, " +
            "       MAX(IF(rfp.update_date <= :end_date, rfp.update_date, NULL)) as maxEndDate " +
            "   FROM removed_favorite_product rfp " +
            "   WHERE rfp.update_date <= :end_date " +
            "   GROUP BY rfp.product_id " +
            ") AS rfpGroup on rfpGroup.product_id = p.id " +
            "LEFT JOIN removed_favorite_product rfpStart " +
            "   ON rfpStart.product_id = p.id AND rfpStart.update_date = rfpGroup.maxStartDate " +
            "LEFT JOIN removed_favorite_product rfpEnd " +
            "   ON rfpEnd.product_id = p.id AND rfpEnd.update_date = rfpGroup.maxEndDate " +

            // left join number add to cart
            "LEFT JOIN (" +
            "   SELECT " +
            "       natc.product_id, " +
            "       MAX(IF(natc.update_date < :start_date, natc.update_date, NULL)) as maxStartDate, " +
            "       MAX(IF(natc.update_date <= :end_date, natc.update_date, NULL)) as maxEndDate " +
            "   FROM number_add_to_cart natc " +
            "   WHERE natc.update_date <= :end_date " +
            "   GROUP BY natc.product_id" +
            ") as natcGroup on natcGroup.product_id = p.id " +
            "LEFT JOIN number_add_to_cart atcStart " +
            "   ON atcStart.product_id = p.id AND atcStart.update_date = natcGroup.maxStartDate " +
            "LEFT JOIN number_add_to_cart atcEnd " +
            "   ON atcEnd.product_id = p.id AND atcEnd.update_date = natcGroup.maxEndDate " +

            // left join total transaction
            "LEFT JOIN (" +
            "   SELECT " +
            "       tt.product_id," +
            "       MAX(IF(tt.update_date < :start_date, tt.update_date, NULL)) as maxStartDate, " +
            "       MAX(IF(tt.update_date <= :end_date, tt.update_date, NULL)) as maxEndDate " +
            "   FROM total_transaction tt " +
            "   WHERE tt.update_date <= :end_date " +
            "   GROUP BY tt.product_id " +
            ") AS ttGroup ON ttGroup.product_id = p.id " +
            "LEFT JOIN total_transaction ttStart " +
            "   ON ttStart.product_id = p.id AND ttStart.update_date = ttGroup.maxStartDate " +
            "LEFT JOIN total_transaction ttEnd " +
            "   ON ttEnd.product_id = p.id AND ttEnd.update_date = ttGroup.maxEndDate " +

            // left join total sales
            "LEFT JOIN (" +
            "   SELECT " +
            "       ts.product_id," +
            "       MAX(IF(ts.update_date < :start_date, ts.update_date, NULL)) as maxStartDate, " +
            "       MAX(IF(ts.update_date <= :end_date, ts.update_date, NULL)) as maxEndDate " +
            "   FROM total_sale ts " +
            "   WHERE ts.update_date <= :end_date " +
            "   GROUP BY ts.product_id" +
            ") AS tsGroup ON tsGroup.product_id = p.id " +
            "LEFT JOIN total_sale tsStart " +
            "   ON tsStart.product_id = p.id AND tsStart.update_date = tsGroup.maxStartDate " +
            "LEFT JOIN total_sale tsEnd " +
            "ON tsEnd.product_id = p.id AND tsEnd.update_date = tsGroup.maxEndDate " +
            // if the product has not been deleted.
            "WHERE p.delete_flag = 0",
            nativeQuery = true
    )
    Page<ProductStatisticsInterface> findProductStatistics(@Param("start_date") String startDate, @Param("end_date") String endDate, Pageable pageable);
}
