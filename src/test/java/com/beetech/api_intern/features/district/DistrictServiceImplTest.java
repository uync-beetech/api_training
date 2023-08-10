package com.beetech.api_intern.features.district;

import com.beetech.api_intern.features.city.City;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The type District service impl test.
 */
@ExtendWith(MockitoExtension.class)
class DistrictServiceImplTest {
    /**
     * The District repository.
     */
    @Mock
    DistrictRepository districtRepository;

    /**
     * The District service.
     */
    @Mock
    DistrictService districtService;

    /**
     * The Districts.
     */
    List<District> districts;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        districtRepository = mock(DistrictRepository.class);
        districtService = new DistrictServiceImpl(districtRepository);
        districts = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            districts.add(District.builder()
                    .id(i)
                    .city(City.builder().id(1).name("Test city").build())
                    .name("Test district " + i)
                    .build());
        }
    }

    /**
     * Tear down.
     */
    @AfterEach
    void tearDown() {
    }

    /**
     * Find all has records in database should return list district.
     */
    @Test
    void findAll_HasRecordsInDatabase_ShouldReturnListDistrict() {
        when(districtRepository.findAllByCityId(any(Integer.class)))
                .thenAnswer(invocationOnMock -> districts);
        assertFalse(districtService.findAll(1).isEmpty());
    }

    /**
     * Find all has no records in database should return empty list.
     */
    @Test
    void findAll_HasNoRecordsInDatabase_ShouldReturnEmptyList() {
        when(districtRepository.findAllByCityId(any(Integer.class)))
                .thenAnswer(invocationOnMock -> new ArrayList<District>());
        assertTrue(districtService.findAll(1).isEmpty());
    }
}