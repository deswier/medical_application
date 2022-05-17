package com.myapplication.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Note implements Serializable {
    private UUID uuid;
    private String lab;
    @JsonFormat(pattern = "yyyy-MM-dd")
    //   @NotNull
    Date date;
    //   @NotNull
    private String test;
    //    @NotNull
    private String result;
    //  @NotNull
    private String referenceRange;
    private String unit;
    private String comment;

    public Note() {
    }

    public Note(UUID uuid, String lab, @NotNull String test, @NotNull Date date, @NotNull String result, @NotNull String referenceRange, String unit, String comment) {
        this.uuid = uuid;
        this.lab = lab;
        this.test = test;
        this.date = date;
        this.result = result;
        this.referenceRange = referenceRange;
        this.unit = unit;
        this.comment = comment;
    }

    public static String[] referenceRange(String referenceRange) {
        return referenceRange.trim().split("-");
    }

    public boolean isNormalResult() {
        try {
            String[] arrayReferenceRange = referenceRange(referenceRange);
            if (arrayReferenceRange.length == 1) return Objects.equals(arrayReferenceRange[0], String.valueOf(result));
            else if (arrayReferenceRange.length == 2) {
                Double result = Double.parseDouble(this.result);
                double leftRange = Double.parseDouble(arrayReferenceRange[0]);
                double rightRange = Double.parseDouble(arrayReferenceRange[1]);
                return (result.compareTo(leftRange) == 0 || result.compareTo(leftRange) == 1) && (result.compareTo(rightRange) == 0 || result.compareTo(rightRange) == -1);
            } else return true;
        } catch (Exception e) {
            return true;
        }
    }

    @NotNull
    @Override
    public String toString() {
        return lab + " " + test + " " + result + unit + " " + referenceRange + unit + "\n";
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
    public Date getDate() {
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