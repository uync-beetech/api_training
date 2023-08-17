package com.beetech.api_intern.features.carts;

import com.beetech.api_intern.features.carts.cartdetails.CartDetail;
import com.beetech.api_intern.features.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Column(name = "user_note")
    @Setter
    private String userNote;

    @Column(name = "total_price")
    @Builder.Default
    @Setter
    private Double totalPrice = 0D;

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

    public void addDetail(CartDetail cartDetail) {
        cartDetails.add(cartDetail);
        setTotalPrice(getTotalPrice() + cartDetail.getTotalPrice());
    }

    public void plusTotalPrice(Double price) {
        setTotalPrice(getTotalPrice()+price);
    }

    public void minusTotalPrice(Double price) {
        setTotalPrice(getTotalPrice()-price);
    }
}
