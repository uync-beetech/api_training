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
            "COALESCE(pvEnd.viewCount, 0) - COALESCE(pvStart.viewCount, 0) as view, " +
            "COALESCE(afpEnd.addedCount, 0) - COALESCE(afpStart.addedCount, 0) as addedFavorite, " +
            "COALESCE(rfpEnd.removedCount, 0) - COALESCE(rfpStart.removedCount, 0) as removedFavorite, " +
            "COALESCE(atcEnd.addToCartCount, 0) - COALESCE(atcStart.addToCartCount, 0) as numberAddToCarts, " +
            "COALESCE(ttEnd.transactions, 0) - COALESCE(ttStart.transactions, 0) as totalTransactions, " +
            "COALESCE(tsEnd.saleCount, 0) - COALESCE(tsStart.saleCount, 0) as totalSales, " +
            "CASE " +
            "        WHEN COALESCE(pvEnd.viewCount, 0) - COALESCE(pvStart.viewCount, 0) > 0 " +
            "           THEN ABS(CAST((COALESCE(ttEnd.transactions, 0) - COALESCE(ttStart.transactions, 0)) as DOUBLE )) / COALESCE(pvEnd.viewCount, 0) - COALESCE(pvStart.viewCount, 0) " +
            "        ELSE 0" +
            "END AS transactionViewRatio " +
            "FROM Product p " +

            // join product view
            "LEFT JOIN ProductView pvStart " +
            "   ON pvStart.productId = p.id AND pvStart.updateDate = " +
            "(" +
            "SELECT MAX(pv.updateDate) from ProductView pv " +
            "WHERE pv.productId = p.id AND pv.updateDate < :start_date" +
            ")" +
            "LEFT JOIN ProductView pvEnd " +
            "   ON pvEnd.productId = p.id and pvEnd.updateDate = " +
            "(" +
            "SELECT MAX(pv.updateDate) from ProductView pv " +
            "WHERE pv.productId = p.id AND pv.updateDate <= :end_date" +
            ")" +

            // left join added favorite product
            "LEFT JOIN AddedFavoriteProduct afpStart " +
            "   ON afpStart.productId = p.id AND afpStart.updateDate =" +
            "(" +
            "SELECT MAX(afp.updateDate) from AddedFavoriteProduct afp " +
            "WHERE afp.productId = p.id AND afp.updateDate < :start_date" +
            ") " +
            "LEFT JOIN AddedFavoriteProduct afpEnd " +
            "   ON afpEnd.productId = p.id AND afpEnd.updateDate = " +
            "(" +
            "SELECT MAX(afp.updateDate) from AddedFavoriteProduct afp " +
            "WHERE afp.productId = p.id AND afp.updateDate <= :end_date" +
            ") " +

            // left join removed favorite product
            "LEFT JOIN RemovedFavoriteProduct rfpStart " +
            "   ON rfpStart.productId = p.id AND rfpStart.updateDate = " +
            "(" +
            "SELECT MAX(rfp.updateDate) from RemovedFavoriteProduct rfp " +
            "WHERE rfp.productId = p.id AND rfp.updateDate < :start_date" +
            ") " +
            "LEFT JOIN RemovedFavoriteProduct rfpEnd " +
            "   ON rfpEnd.productId = p.id AND rfpEnd.updateDate = " +
            "(" +
            "SELECT MAX(rfp.updateDate) from RemovedFavoriteProduct rfp " +
            "WHERE rfp.productId = p.id AND rfp.updateDate <= :end_date" +
            ") " +

            // left join number add to cart
            "LEFT JOIN NumberAddToCart atcStart " +
            "   ON atcStart.productId = p.id AND atcStart.updateDate = " +
            "(" +
            "SELECT MAX (ac.updateDate) from NumberAddToCart ac " +
            "WHERE ac.productId = p.id AND ac.updateDate < :start_date" +
            ") " +
            "LEFT JOIN NumberAddToCart atcEnd " +
            "   ON atcEnd.productId = p.id AND atcEnd.updateDate = " +
            "(" +
            "SELECT MAX(ac.updateDate) from NumberAddToCart ac " +
            "WHERE ac.productId = p.id AND ac.updateDate <= :end_date " +
            ")" +

            // left join total transaction
            "LEFT JOIN TotalTransaction ttStart " +
            "   ON ttStart.productId = p.id AND ttStart.updateDate = " +
            "(" +
            "SELECT MAX(tt.updateDate) from TotalTransaction tt " +
            "WHERE tt.productId = p.id AND tt.updateDate < :start_date" +
            ") " +
            "LEFT JOIN TotalTransaction ttEnd " +
            "   ON ttEnd.productId = p.id AND ttEnd.updateDate = " +
            "(" +
            "SELECT MAX(tt.updateDate) from TotalTransaction tt " +
            "WHERE tt.productId = p.id AND tt.updateDate <= :end_date" +
            ") " +

            // left join total sales
            "LEFT JOIN TotalSale tsStart " +
            "   ON tsStart.productId = p.id AND tsStart.updateDate = " +
            "( " +
            "SELECT MAX(ts1.id.updateDate) from TotalSale ts1 " +
            "WHERE ts1.productId = p.id AND ts1.updateDate < :start_date" +
            ") " +
            "LEFT JOIN TotalSale tsEnd " +
            "ON tsEnd.productId = p.id AND tsEnd.updateDate = " +
            "( " +
            "SELECT MAX(ts2.updateDate) from TotalSale ts2 " +
            "WHERE ts2.productId = p.id " +
            "AND ts2.updateDate <= :end_date " +
            ") " +
            // if the product has not been deleted.
            "WHERE p.deleted = false "
    )
    Page<ProductStatisticsInterface> findProductStatistics(@Param("start_date") String startDate, @Param("end_date") String endDate, Pageable pageable);
}
