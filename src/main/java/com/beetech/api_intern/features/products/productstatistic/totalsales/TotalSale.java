package com.beetech.api_intern.features.products.productstatistic.totalsales;

import com.beetech.api_intern.features.products.productstatistic.ProductStatisticKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "total_sale")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TotalSale implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ProductStatisticKey id;

    @Column(name = "sale_count", columnDefinition = "BIGINT Default 0")
    @Builder.Default
    private Long saleCount = 0L;

    @Column(name = "product_id", insertable = false, updatable = false)
    private Long productId;

    @Column(name = "update_date", nullable = false, insertable = false, updatable = false)
    private String updateDate;

}
