package com.beetech.api_intern.features.district;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type District service.
 */
@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {
    private final DistrictRepository districtRepository;

    @Override
    public List<District> findAll(Integer cityId) {
        // call repo method to find all districts by cityId
        return districtRepository.findAllByCityId(cityId);
    }
}
