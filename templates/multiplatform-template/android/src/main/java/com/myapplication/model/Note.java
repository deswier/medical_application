package com.myapplication.model;

import android.os.Build;
import com.myapplication.exception.DataException;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Note implements Serializable {
    private UUID uuid;
    private String lab;
    @NotNull
    private String test;
    @NotNull
    LocalDate date;
    @NotNull
    private String result;
    @NotNull
    private String referenceRange;
    private String unit;
    private String comment;

    public Note(UUID uuid, String lab, @NotNull String test, @NotNull Calendar date, @NotNull String result, @NotNull String referenceRange, String unit, String comment) {
        this.uuid = uuid;
        this.lab = lab;
        this.test = test;
        this.date = convert(date);
        this.result = result;
        this.referenceRange = referenceRange;
        this.unit = unit;
        this.comment = comment;
    }

    public Note(UUID uuid, String lab, @NotNull String test, @NotNull LocalDate date, @NotNull String result, @NotNull String referenceRange, String unit, String comment) {
        this.uuid = uuid;
        this.lab = lab;
        this.test = test;
        this.date = date;
        this.result = result;
        this.referenceRange = referenceRange;
        this.unit = unit;
        this.comment = comment;
    }

    public boolean isNormalResult() throws DataException {
        try {
            String[] arrayReferenceRange = referenceRange(referenceRange);
            if (arrayReferenceRange.length == 1) return Objects.equals(arrayReferenceRange[0], String.valueOf(result));
            else if (arrayReferenceRange.length == 2) {
                double result = Double.parseDouble(this.result);
                double leftRange = Double.parseDouble(arrayReferenceRange[0]);
                double rightRange = Double.parseDouble(arrayReferenceRange[1]);
                return result <= rightRange && result >= leftRange;
            } else return true;
        } catch (Exception e) {
            return true;
        }
    }

    public static String[] referenceRange(String referenceRange) throws DataException {
        String[] r = referenceRange.trim().split("-");
        if (r.length == 1 || r.length == 2) return r;
        else throw new DataException("Invalid reference range " + referenceRange);
    }

    @NotNull
    @Override
    public String toString() {
        return lab + " " + test + " " + result + unit + " " + referenceRange + unit + "\n";
    }

    private LocalDate convert(Calendar calendar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()).toLocalDate();
        } else return null;
    }

    @NotNull
    public String getTest() {
        return test;
    }

    @NotNull
    public String getResult() {
        return result;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getLab() {
        return lab;
    }

    @NotNull
    public LocalDate getDate() {
        return date;
    }

    @NotNull
    public String getReferenceRange() {
        return referenceRange;
    }

    public String getUnit() {
        return unit;
    }

    public String getComment() {
        return comment;
    }
}