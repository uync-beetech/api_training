package com.beetech.api_intern.features.numaddtocart;

import com.beetech.api_intern.features.products.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "count", columnDefinition = "BIGINT Default 1")
    @Builder.Default
    private Long count = 1L;

    @Column(name = "version", columnDefinition = "BIGINT Default 1")
    @Builder.Default
    @Version
    private Long version = 1L;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @UpdateTimestamp
    private LocalDateTime updated;

    public void plus() {
        setCount(getCount() + 1);
    }
}
