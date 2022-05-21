package com.myapplication.tools;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;

public class DateParser {
    private static final String PATTERN = "dd-MM-yyyy";
    private static final String SEPARATOR = "-";

    public static String convertToString(Calendar date) {
        return date.get(Calendar.DAY_OF_MONTH) + SEPARATOR + date.get(Calendar.MONTH) + SEPARATOR + date.get(Calendar.YEAR);
    }

    public static String convertToString(Date date) {
        Calendar cal = convertToCalendar(date);
        if (cal != null)
            return convertToString(cal);
        else return "";
    }

    public static Calendar convertToCalendar(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } else return null;
    }

    public static String convertToShortString(@NotNull Date date) {
        Calendar cal = convertToCalendar(date);
        if (cal != null)
            return cal.get(Calendar.DAY_OF_MONTH) + SEPARATOR + cal.get(Calendar.MONTH) + SEPARATOR + getShortYear(cal.get(Calendar.YEAR));
        else return "";
    }

    public static String getShortYear(int year) {
        String s = String.valueOf(year);
        if (s.length() > 2)
            return s.substring(s.length() - 2);
        else
            return s;
    }
}