package com.beetech.api_intern.features.products.productstatistic;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ProductStatisticKey implements Serializable {
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "update_date", nullable = false, columnDefinition = "VARCHAR(8)")
    private String updateDate;

}
