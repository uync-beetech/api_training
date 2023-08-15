package com.beetech.api_intern.features.district;

import java.util.List;

/**
 * The interface District service.
 */
public interface DistrictService {
    /**
     * Find all list.
     *
     * @param cityId the city id
     * @return the list
     */
    List<District> findAll(Integer cityId);
}
