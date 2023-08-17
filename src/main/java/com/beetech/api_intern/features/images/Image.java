package com.beetech.api_intern.features.images;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@Table(name = "image")
@NoArgsConstructor
@AllArgsConstructor
public class Image implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "path")
    @Getter
    private String path;

    @Column(name = "name")
    @Getter
    private String name;

    @Column(name = "thumbnail_flag", columnDefinition = "TINYINT default 1")
    @Builder.Default
    private boolean thumbnail = false;

    @CreationTimestamp
    @Getter
    private LocalDateTime created;

    @UpdateTimestamp
    @Getter
    private LocalDateTime updated;
}
