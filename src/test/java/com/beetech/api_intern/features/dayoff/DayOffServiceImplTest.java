package com.beetech.api_intern.features.dayoff;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DayOffServiceImplTest {

    @Mock
    DayOffRepository dayOffRepository;

    @InjectMocks
    DayOffServiceImpl dayOffService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Test today is day off")
    void isTodayDayOff_WhenTodayIsDayOff() {
        when(dayOffRepository.existsDayOffByDateEquals(any(LocalDate.class)))
                .then((Answer<Boolean>) invocationOnMock -> true);
        assertTrue(dayOffService.isTodayDayOff());
    }

    @Test
    void isTodayDayOff_WhenTodayIsNotDayOff() {
        when(dayOffRepository.existsDayOffByDateEquals(any(LocalDate.class)))
                .then((Answer<Boolean>) invocationOnMock -> false);
        assertFalse(dayOffService.isTodayDayOff());
    }
}