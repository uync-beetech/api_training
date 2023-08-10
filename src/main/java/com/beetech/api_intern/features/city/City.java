package com.beetech.api_intern.features.city;

import com.beetech.api_intern.features.district.District;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type City.
 */
@Entity
@Table(name = "city_master")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Getter
    private int id;

    @Column(unique = true)
    @Getter
    private String name;

    @OneToMany(mappedBy = "city")
    private List<District> districts;
}
