package com.beetech.api_intern.features.categories;

import com.beetech.api_intern.features.images.Image;
import com.beetech.api_intern.features.products.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;


    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "category_image",
            joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id")
    )
    @Setter
    @Builder.Default
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private List<Product> products = new ArrayList<>();

    @CreationTimestamp
    @Getter
    private LocalDateTime created;

    @UpdateTimestamp
    @Getter
    private LocalDateTime updated;

    public String getImageDirectory() {
        return "CAT_" + getId();
    }
}
