package com.beetech.api_intern.features.carts;

import com.beetech.api_intern.features.carts.cartdetails.CartDetail;
import com.beetech.api_intern.features.products.Product;
import com.beetech.api_intern.features.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Cart Entity
 */
@Entity
@Table(name = "cart")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cart implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "token", length = 20, unique = true)
    @Setter
    private String token;

    @Column(name = "total_price")
    @Builder.Default
    @Setter
    private Double totalPrice = 0D;

    @Column(name = "version_no", columnDefinition = "BIGINT Default 1")
    @Builder.Default
    @Setter
    private Long versionNo = 1L;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @Builder.Default
    @ToString.Exclude
    private List<CartDetail> cartDetails = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    @Setter
    private User user;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

    /**
     * Add detail.
     *
     * @param cartDetail the cart detail
     */
    public void addDetail(CartDetail cartDetail) {
        cartDetails.add(cartDetail);
        setTotalPrice(getTotalPrice() + cartDetail.getTotalPrice());
    }

    /**
     * Plus total price.
     *
     * @param price the price
     */
    public void plusTotalPrice(Double price) {
        setTotalPrice(getTotalPrice() + price);
    }

    /**
     * Minus total price.
     *
     * @param price the price
     */
    public void minusTotalPrice(Double price) {
        setTotalPrice(getTotalPrice() - price);
    }

    public void plusOne() {
        setVersionNo(getVersionNo() + 1);
    }
}
