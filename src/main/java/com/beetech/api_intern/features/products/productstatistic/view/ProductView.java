package com.beetech.api_intern.features.products.productstatistic.view;

import com.beetech.api_intern.features.products.productstatistic.ProductStatisticKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "product_view")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductView implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ProductStatisticKey id;

    @Column(name = "view_count", columnDefinition = "BIGINT Default 0")
    @Builder.Default
    private Long viewCount = 0L;

    @Column(name = "update_date", nullable = false, insertable = false, updatable = false)
    private String updateDate;

    @Column(name = "product_id", nullable = false, insertable = false, updatable = false)
    private Long productId;

}
