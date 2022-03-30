package com.myapplication.model;

import com.myapplication.exception.DataException;
import com.myapplication.exception.VersionException;
import com.myapplication.tools.DateParser;
import lombok.Data;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Note implements Serializable {
    private UUID uuid;
    private String lab;
    private FullName name;
    private String test;
    private Date date;
    private String result;
    private String referenceRange;
    private String unit;
    private String comment;

    public Note(UUID uuid, String lab, FullName name, String test, Date date, String result, String referenceRange, String unit, String comment) {
        this.uuid = uuid;
        this.lab = lab;
        this.test = test;
        this.name = name;
        this.date = date;
        this.result = result;
        this.referenceRange = referenceRange;
        this.unit = unit;
        this.comment = comment;
    }

    public Note(UUID uuid, String lab, FullName name, String test, LocalDate date, String result, String referenceRange, String unit, String comment) throws VersionException {
        new Note(uuid, lab, name, test, DateParser.convertToDate(date), result, referenceRange, unit, comment);
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

    private static String[] referenceRange(String referenceRange) throws DataException {
        String[] r = referenceRange.trim().split("-");
        if (r.length == 1 || r.length == 2) return r;
        else throw new DataException("Invalid reference range " + referenceRange);
    }

    @Override
    public String toString() {
        return lab + " " + name.getFullName() + " " + test + " " + result + unit + " " + referenceRange + unit + "\n";
    }

    public Note() {
    }

    public String getTest() {
        return test;
    }

    public String getResult() {
        return result;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getLab() {
        return lab;
    }

    public FullName getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

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
