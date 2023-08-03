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

    @Value("${working.time.start}")
    private String start;

    @Value("${working.time.end}")
    private String end;

    public List<DayOfWeek> getDaysOfWeek() {
        return Arrays.stream(days.split(",")).map(DayOfWeek::valueOf).toList();
    }


    public LocalTime getStartTime() {
        return LocalTime.parse(start);
    }

    public LocalTime getEndTime() {
        return LocalTime.parse(end);
    }
}
