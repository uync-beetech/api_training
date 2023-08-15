package com.beetech.api_intern.features.carts.cartdetails;

import com.beetech.api_intern.features.carts.Cart;
import com.beetech.api_intern.features.products.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart_detail")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter
    private Long id;

    @Column
    @Setter
    private Long quantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "total_price")
    @Setter
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    @Setter
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

    @PrePersist
    public void calculateTotalPrice() {
        price = product.getPrice();
        totalPrice = price * quantity;
    }

    public void updateTotalPrice() {
        setTotalPrice(getQuantity() * getPrice());
    }

    public void updateQuantity(Long quantity) {
        setQuantity(quantity);
        setTotalPrice(quantity * getPrice());
    }
}
