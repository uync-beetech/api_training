package com.beetech.api_intern.features.city;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The type City service impl test.
 */
@ExtendWith(MockitoExtension.class)
class CityServiceImplTest {
    /**
     * The City repository.
     */
    @Mock
    CityRepository cityRepository;

    /**
     * The City service.
     */
    @Mock
    CityService cityService;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        cityRepository = mock(CityRepository.class);
        cityService = new CityServiceImpl(cityRepository);
    }

    /**
     * Tear down.
     */
    @AfterEach
    void tearDown() {
    }

    /**
     * Find all has records in database should return list city.
     */
    @Test
    void findAll_HasRecordsInDatabase_ShouldReturnListCity() {
        List<City> cities = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            cities.add(City.builder()
                    .id(i)
                    .name("Test city " + i)
                    .build());
        }
        when(cityRepository.findAll()).thenAnswer(invocationOnMock -> cities);

        assertFalse(cityService.findAll().isEmpty());
    }

    /**
     * Find all has no records in database should return empty list.
     */
    @Test
    void findAll_HasNoRecordsInDatabase_ShouldReturnEmptyList() {
        when(cityRepository.findAll()).thenAnswer(invocationOnMock -> new ArrayList<City>());
        assertTrue(cityService.findAll().isEmpty());
    }
}