package com.beetech.api_intern.features.carts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByToken(String token);

    @Modifying
    @Query("UPDATE Cart c set c.totalPrice = :totalPrice where c.id = :cartId")
    void updateTotalPriceById(Double totalPrice, Long cartId);
}
