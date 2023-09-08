package com.beetech.api_intern.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Date time formatter utils.
 */
public class DateTimeFormatterUtils {
    private static final String DATE_STRING_FORMAT = "yyyyMMdd";

    private DateTimeFormatterUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Format local date time string.
     *
     * @param localDateTime the local date time
     * @return the string
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Convert local date to string
     *
     * @param date the date
     * @return the string
     */
    public static String convertLocalDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_STRING_FORMAT);
        return date.format(formatter);
    }

    /**
     * Convert string to local date
     *
     * @param date the date
     * @return the local date
     */
    public static LocalDate convertStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_STRING_FORMAT);
        return LocalDate.parse(date, formatter);
    }

}
