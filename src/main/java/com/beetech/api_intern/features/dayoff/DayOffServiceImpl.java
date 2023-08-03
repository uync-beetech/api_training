package com.beetech.api_intern.features.dayoff;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * The type Day off service.
 */
@Service
@RequiredArgsConstructor
public class DayOffServiceImpl implements DayOffService {
    /**
     * inject day off repository
     */
    private final DayOffRepository dayOffRepository;

    /**
     * Check today is day off or not
     * @return boolean
     */
    @Override
    public boolean isTodayDayOff() {
        // get today
        LocalDate today = LocalDate.now();
        return dayOffRepository.existsDayOffByDateEquals(today);
    }
}
