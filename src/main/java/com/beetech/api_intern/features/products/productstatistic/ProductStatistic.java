package com.beetech.api_intern.features.products.productstatistic;

import com.beetech.api_intern.features.products.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_statistic")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductStatistic implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "view", columnDefinition = "BIGINT Default 0")
    @Builder.Default
    private Long view = 0L;

    @Column(name = "number_add_to_carts", columnDefinition = "BIGINT Default 0")
    @Builder.Default
    private Long numberAddToCarts = 0L;

    @Column(name = "total_transactions", columnDefinition = "BIGINT Default 0")
    @Builder.Default
    private Long totalTransactions = 0L;

    @Column(name = "total_sales", columnDefinition = "BIGINT Default 0")
    @Builder.Default
    private Long totalSales = 0L;

    @Version
    @Column(name = "version", columnDefinition = "BIGINT Default 1")
    @Builder.Default
    private Long version = 1L;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @UpdateTimestamp
    private LocalDateTime updated;

    public void plusView() {
        setView(getView() + 1);
    }

    public void plusTotalTransaction() {
        setTotalTransactions(getTotalTransactions() + 1);
    }

    public void plusNumberAddToCart() {
        setNumberAddToCarts(getNumberAddToCarts() + 1);
    }

    public void plusTotalSales(Long quantity) {
        setTotalSales(getTotalSales() + quantity);
    }
}
