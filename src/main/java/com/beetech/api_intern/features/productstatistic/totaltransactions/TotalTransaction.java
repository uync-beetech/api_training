package com.beetech.api_intern.features.productstatistic.totaltransactions;

import com.beetech.api_intern.features.productstatistic.ProductStatisticKey;
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
}