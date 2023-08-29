package com.beetech.api_intern.features.products.favoriteproduct.removedfavorite;

import com.beetech.api_intern.features.products.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "removed_favorite_product")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RemovedFavoriteProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "count", columnDefinition = "BIGINT Default 0")
    private Long count = 0L;

    @CreationTimestamp
    private LocalDateTime created;
}
