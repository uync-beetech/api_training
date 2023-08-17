package com.beetech.api_intern.features.orders.orderdetail;

import com.beetech.api_intern.features.orders.Order;
import com.beetech.api_intern.features.products.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_detail")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "quantity")
    @Setter
    private Long quantity;

    @Column(name = "price")
    @Setter
    private Double price;

    @Column(name = "total_price")
    @Setter
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

    @PrePersist
    public void initTotalPrice() {
        setPrice(product.getPrice());
        setTotalPrice(getPrice() * getQuantity());
    }
}
