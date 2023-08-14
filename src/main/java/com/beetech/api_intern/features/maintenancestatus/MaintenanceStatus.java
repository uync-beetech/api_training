package com.beetech.api_intern.features.maintenancestatus;

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
@Table(name = "maintenance_status")
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceStatus implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "maintenance_flag", columnDefinition = "BOOLEAN DEFAULT false")
    @Getter
    private boolean flag = false;

    @Column(name = "maintenance_time", nullable = false)
    @Getter
    private LocalDateTime time;

    @Column(name = "remark", length = 300)
    private String remark;

    @Column(name = "created")
    @CreationTimestamp
    private LocalDateTime created;

    @Column(name = "updated")
    @UpdateTimestamp
    private LocalDateTime updated;
}
