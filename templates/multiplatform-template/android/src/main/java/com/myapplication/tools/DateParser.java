package com.myapplication.tools;

import java.time.LocalDate;
import java.util.Calendar;

public class DateParser {
    private static final String PATTERN = "dd-MM-yyyy";
    private static final String SEPARATOR = "-";

    public static String convertToString(Calendar date) {
        return date.get(Calendar.DAY_OF_MONTH) + SEPARATOR + date.get(Calendar.MONTH) + SEPARATOR + date.get(Calendar.YEAR);
    }

    public static String convertToString(LocalDate date) {
        return date.toString();
    }
}