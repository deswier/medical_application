package com.myapplication.tools;

import com.myapplication.exception.VersionException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateParser {

    public static LocalDate parseStringToDate(String s) throws VersionException {
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(s, formatter);
        } else
            throw new VersionException("Sdk version" + android.os.Build.VERSION.SDK_INT + " does not satisfy the conditions " + ">= android.os.Build.VERSION_CODES.O");
    }

    public static String parseDateToString(LocalDate date) {
        return String.valueOf(date);
    }
}
