package com.beetech.api_intern.features.productstatistic.totalsales;

import com.beetech.api_intern.features.productstatistic.ProductStatisticKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "total_sale")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TotalSale {
    @EmbeddedId
    private ProductStatisticKey id;

    @Column(name = "sale_count", columnDefinition = "BIGINT Default 0")
    @Builder.Default
    private Long saleCount = 0L;
}
