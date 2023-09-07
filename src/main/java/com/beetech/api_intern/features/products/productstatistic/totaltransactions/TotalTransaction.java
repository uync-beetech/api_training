package com.beetech.api_intern.features.products.productstatistic.totaltransactions;

import com.beetech.api_intern.features.products.productstatistic.ProductStatisticKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "total_transaction")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TotalTransaction {
    @EmbeddedId
    private ProductStatisticKey id;

    @Column(name = "transactions", columnDefinition = "BIGINT Default 0")
    @Builder.Default
    private Long transactions = 0L;

    @Column(name = "product_id", nullable = false, updatable = false, insertable = false)
    private Long productId;

    @Column(name = "update_date", nullable = false, updatable = false, insertable = false)
    private String updateDate;
}