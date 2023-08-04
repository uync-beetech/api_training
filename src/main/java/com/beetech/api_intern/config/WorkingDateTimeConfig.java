package com.beetech.api_intern.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Getter
@Configuration
public class WorkingDateTimeConfig {

    @Value("${working.days}")
    private String days;
    private List<DayOfWeek> dayOfWeeks = null;

    @Value("${working.time.start}")
    private String start;
    private LocalTime startTime = null;

    @Value("${working.time.end}")
    private String end;
    private LocalTime endTime = null;

    public List<DayOfWeek> getDaysOfWeek() {
        if (dayOfWeeks == null) {
            dayOfWeeks = Arrays.stream(days.split(","))
                    .map(DayOfWeek::valueOf)
                    .toList();
        }
        return dayOfWeeks;
    }


    public LocalTime getStartTime() {
        if (startTime == null) {
            StringBuilder sbStartTime = new StringBuilder(start);
            startTime = LocalTime.parse(sbStartTime.insert(2, ':'));
        }
        return startTime;
    }

    public LocalTime getEndTime() {
        if (endTime == null) {
            StringBuilder sbEndTime = new StringBuilder(end);
            endTime = LocalTime.parse(sbEndTime.insert(2, ':'));
        }
        return endTime;
    }
}
