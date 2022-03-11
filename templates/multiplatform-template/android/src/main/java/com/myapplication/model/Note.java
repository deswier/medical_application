package com.myapplication.model;

import lombok.Data;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.UUID;

@Getter
public class Note {
    @NotNull private UUID uuid;
    private String lab;
    @NotNull private FullName name;
    @NotNull private String test;
    @NotNull private Date date;
    @NotNull  private String result;
    @NotNull private String referenceRange;
    @NotNull private String unit;
    private String comment;

    public Note(UUID uuid, String lab,FullName name,  String test,Date date, String result, String referenceRange, String unit, String comment) {
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
}
