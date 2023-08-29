package com.beetech.api_intern.features.products.favoriteproduct.addedfavorite;

import com.beetech.api_intern.features.products.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "added_favorite_product")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddedFavoriteProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "count", columnDefinition = "BIGINT Default 0")
    @Builder.Default
    private Long count = 0L;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @CreationTimestamp
    private LocalDateTime created;
}
