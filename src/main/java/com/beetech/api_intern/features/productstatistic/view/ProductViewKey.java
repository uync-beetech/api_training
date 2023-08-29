package com.beetech.api_intern.features.productstatistic.view;

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
public class ProductViewKey implements Serializable {
    private Long productId;

    @Column(name = "date_view", nullable = false, columnDefinition = "VARCHAR(8)")
    private String dateView;

}
