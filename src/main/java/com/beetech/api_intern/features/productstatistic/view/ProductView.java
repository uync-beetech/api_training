package com.beetech.api_intern.features.productstatistic.view;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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
    private ProductViewKey id;

    @Column(name = "view_count", columnDefinition = "BIGINT Default 0")
    @Builder.Default
    private Long viewCount = 0L;

    @UpdateTimestamp
    private LocalDateTime updated;

    public void plusView() {
        setViewCount(getViewCount() + 1);
    }
}
