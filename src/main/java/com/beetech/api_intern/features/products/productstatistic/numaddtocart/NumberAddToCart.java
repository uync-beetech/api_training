package com.beetech.api_intern.features.products.productstatistic.numaddtocart;

import com.beetech.api_intern.features.products.productstatistic.ProductStatisticKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "number_add_to_cart")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NumberAddToCart implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ProductStatisticKey id;

    @Column(name = "add_to_cart_count", columnDefinition = "BIGINT Default 0")
    @Builder.Default
    private Long addToCartCount = 0L;

    @Column(name = "product_id", nullable = false, updatable = false, insertable = false)
    private Long productId;

    @Column(name = "update_date", nullable = false, updatable = false, insertable = false)
    private String updateDate;
}
