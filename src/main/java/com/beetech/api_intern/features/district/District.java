package com.beetech.api_intern.features.district;

import com.beetech.api_intern.features.city.City;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "district_master")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
