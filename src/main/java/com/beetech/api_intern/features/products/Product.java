package com.beetech.api_intern.features.products;

import com.beetech.api_intern.features.categories.Category;
import com.beetech.api_intern.features.images.Image;
import com.beetech.api_intern.features.products.productstatistic.ProductStatistic;
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

@Entity
@Table(name = "product")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sku")
    @Setter
    private String sku;

    @Column(name = "old_sku")
    @Setter
    private String oldSku;

    @Column(name = "detail_info")
    private String detailInfo;

    @Column(name = "price")
    private Double price;

    @Column(name = "delete_flag", columnDefinition = "TINYINT(3) Default 0")
    @Setter
    @Builder.Default
    private boolean deleted = false;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_image",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id")
    )
    @Setter
    @Builder.Default
    private List<Image> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    @Setter
    private Category category;

    @OneToOne(mappedBy = "product")
    @Setter
    private ProductStatistic productStatistic;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

    public Optional<Image> getThumbnailImage() {
        return getImages().stream().filter(Image::isThumbnail).findFirst();
    }

}
