package com.myapplication.tools;

import android.annotation.SuppressLint;
import android.os.Build;
import com.myapplication.exception.DataException;
import com.myapplication.exception.VersionException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateParser {
    private static final String PATTERN = "dd-MM-yyyy";
    private static final String SEPARATOR = "-";

    public static LocalDate convertToLocalDate(String s) throws VersionException, DataException {
        try {
            DateTimeFormatter formatter = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern(PATTERN);
                return LocalDate.parse(s, formatter);
            } else
                throw versionException();
        } catch (RuntimeException e) {
            throw dataException(s);
        }
    }

    public static Date convertToDate(String s) throws VersionException, DataException {
        return convertToDate(convertToLocalDate(s));
    }

    private static String separatorWithBackspace() {
        return " " + SEPARATOR + " ";
    }

    private static DataException dataException(String date) {
        return new DataException("date " + date + " is invalid");
    }

    public static String convertToString(LocalDate date) {
        return String.valueOf(date);
    }

    public static String convertToString(Date date) {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat(PATTERN);
        return df.format(date);
    }

    public static String convertToTextDate(Date date) throws VersionException {
        LocalDate lDate=convertToLocalDate(date);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return lDate.getDayOfMonth() + " " + lDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)+" "+lDate.getYear();
        }
        else throw versionException();

    }

    public static LocalDate convertToLocalDate(Date dateToConvert) throws VersionException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return dateToConvert.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        } else throw versionException();
    }

    public static Date convertToDate(LocalDate dateToConvert) throws VersionException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Date.from(dateToConvert.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
        } else throw versionException();
    }

    public static Date convertToDate(int day, int month, int year) throws VersionException {
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);
            return cal.getTime();
        } catch (RuntimeException e) {
            throw versionException();
        }
    }

    public static boolean isDate(String date) throws VersionException {
        try {
            convertToLocalDate(date);
            return true;
        } catch (RuntimeException | DataException e) {
            return false;
        }
    }

    public static String getDay(LocalDate date) throws VersionException {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            return String.valueOf(date.getDayOfMonth());
        } else throw versionException();
    }

    public static String getMonth(LocalDate date) throws VersionException {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int month = date.getMonth().getValue();
            if (month < 10) return "0" + month;
            else return String.valueOf(month);
        } else throw versionException();
    }
    
    private static VersionException versionException() {
        return new VersionException("Invalid SDK version " + Build.VERSION.SDK_INT);
    }
}