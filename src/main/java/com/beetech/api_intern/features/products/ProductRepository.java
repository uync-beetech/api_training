package com.beetech.api_intern.features.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p from Product p where (p.category.id = :categoryId) and (p.deleted = false) and ((p.name like %:searchKey%) or (p.sku like %:searchKey%))")
    Page<Product> findByCategoryIdAndSearchKey(@Param("categoryId") Long categoryId, @Param("searchKey") String searchKey, Pageable pageable);

    Optional<Product> findBySkuAndDeletedIsFalse(String sku);
}
