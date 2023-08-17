package com.beetech.api_intern.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterUtils {
    private DateTimeFormatterUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String localDateToString(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        return localDate.format(formatter);
    }

    public static LocalDate convertStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        return LocalDate.parse(date, formatter);
    }

    public static LocalDate convertBirthdayString(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(date, formatter);
    }
}
