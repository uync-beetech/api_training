package com.beetech.api_intern.features.products.productstatistic.view;

import com.beetech.api_intern.features.products.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_view")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name="count", columnDefinition = "BIGINT Default 0")
    @Builder.Default
    private Long count = 0L;

    @CreationTimestamp
    private LocalDateTime created;

    public void plusView() {
        setCount(getCount() + 1);
    }
}
