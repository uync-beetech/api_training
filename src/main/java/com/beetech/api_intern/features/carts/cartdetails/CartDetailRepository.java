package com.beetech.api_intern.features.carts.cartdetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    @Query("SELECT SUM(cd.quantity) FROM CartDetail cd where cd.cart.id = :cartId")
    Long getQuantityByCart(Long cartId);

    void deleteByCartId(Long cartId);

    Optional<CartDetail> findByIdAndCartId(Long id, Long cartId);

    Optional<CartDetail> findByCartIdAndProductId(Long cartId, Long productId);
}
