package com.myapplication.tools;

import android.os.Build;
import com.myapplication.exception.VersionException;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

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

    public static String parseDateToString(Date date) {
        return String.valueOf(date);
    }

    public static String getShortDate(LocalDate date) throws VersionException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return date.getDayOfMonth() + "." + getMonth(date) + " " +
                    getYear(date);
        } else throw new VersionException("Invalid SDK version " + Build.VERSION.SDK_INT);
    }

    public static LocalDate convertToLocalDate(Date dateToConvert) throws VersionException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return dateToConvert.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        } else throw new VersionException("Invalid SDK version " + Build.VERSION.SDK_INT);
    }

    public static Date convertToDate(LocalDate dateToConvert) throws VersionException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Date.from(dateToConvert.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
        } else throw new VersionException("Invalid SDK version " + Build.VERSION.SDK_INT);

    }

    public static String getShortDate(Date date) throws VersionException {
        return getShortDate(convertToLocalDate(date));
    }

    public static String getDay(LocalDate date) throws VersionException {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            return String.valueOf(date.getDayOfMonth());
        } else throw new VersionException("Invalid SDK version " + Build.VERSION.SDK_INT);
    }

    public static String getMonth(LocalDate date) throws VersionException {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int month = date.getMonth().getValue();
            if (month < 10) return "0" + month;
            else return String.valueOf(month);
        } else throw new VersionException("Invalid SDK version " + Build.VERSION.SDK_INT);
    }

    public static String getYear(LocalDate date) throws VersionException {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int yearDate = date.getYear();
            int yearCurrent = LocalDate.now().getYear();
            if (yearDate == yearCurrent) return "";
            else return String.valueOf(yearDate);
        } else throw new VersionException("Invalid SDK version " + Build.VERSION.SDK_INT);
    }
}