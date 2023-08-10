package com.beetech.api_intern.features.district;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface District repository.
 */
@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
    /**
     * Find all by city id list.
     *
     * @param cityId the city id
     * @return the list
     */
    List<District> findAllByCityId(Integer cityId);
}
