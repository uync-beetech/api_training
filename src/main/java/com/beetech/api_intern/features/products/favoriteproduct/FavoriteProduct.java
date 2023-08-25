package com.beetech.api_intern.features.products.favoriteproduct;

import com.beetech.api_intern.features.products.Product;
import com.beetech.api_intern.features.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "favorite_product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class FavoriteProduct implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "added", columnDefinition = "TINYINT(3) DEFAULT 1")
    @Builder.Default
    private boolean added = true;

    @Column(name = "added_count", columnDefinition = "BIGINT DEFAULT 1")
    @Builder.Default
    private Long addedCount = 1L;

    @Column(name = "removed_count", columnDefinition = "BIGINT DEFAULT 0")
    @Builder.Default
    private Long removedCount = 0L;

    @UpdateTimestamp
    private LocalDateTime updated;

    public void plusAdded() {
        setAddedCount(getAddedCount() + 1);
    }

    public void plusRemoved() {
        setRemovedCount(getRemovedCount() + 1);
    }
}
