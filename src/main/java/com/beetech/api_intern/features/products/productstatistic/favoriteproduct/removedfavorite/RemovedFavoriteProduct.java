package com.beetech.api_intern.features.products.productstatistic.favoriteproduct.removedfavorite;

import com.beetech.api_intern.features.products.productstatistic.ProductStatisticKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "removed_favorite_product")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RemovedFavoriteProduct implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ProductStatisticKey id;

    @Column(name = "removed_count", columnDefinition = "BIGINT Default 0")
    private Long removedCount = 0L;

    @Column(name = "product_id", nullable = false, updatable = false, insertable = false)
    private Long productId;

    @Column(name = "update_date", nullable = false, updatable = false, insertable = false)
    private String updateDate;
}
