package com.myapplication.model;

import com.myapplication.exception.DataException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Locale;
import java.util.regex.Pattern;

@Getter
@Setter
@Data
public class FullName implements Serializable {
    public String firstName;
    public String secondName;
//    private static final String regexNameRussian = ("^([A-Za]+$)");
//    private static final String regexNameEnglish = ("^[А-Яa]+$");

    private static final String regexName = ("([А-Я]{1}[а-яё]{1,23}|[A-Z]{1}[a-z]{1,23})");

    public FullName(String firstName, String secondName) throws DataException {
        setFirstName(firstName);
        setSecondName(secondName);
    }

    public FullName() throws DataException {
        firstName = "";
        secondName = "";
    }

    public String getCorrectName(String name) throws DataException {
        if (isCorrectName(name))
            return firstUpperCase(name);
        else throw new DataException("Name is invalid " + name);
    }

    public void setFirstName(String firstName) throws DataException {
        this.firstName = getCorrectName(firstName);
    }

    public void setSecondName(String secondName) throws DataException {
        this.secondName = getCorrectName(secondName);
    }

    public FullName(String fullName) throws Exception {
        String[] names = getFirstSecondName(fullName);
        if (isCorrectName(firstName) && isCorrectName(secondName))
            this.firstName = firstUpperCase(firstName);
        this.secondName = firstUpperCase(secondName);
    }


    private String[] getFirstSecondName(String fullName) throws Exception {
        String[] names = fullName.split(" ");
        if (names.length != 2) throw new Exception();
        else return names;
    }

    private String firstUpperCase(String word) {
        if (word == null || word.isEmpty()) return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase(Locale.ROOT);
    }

    boolean isCorrectName(String name) {
        try {
            return Pattern.compile(FullName.regexName).matcher(name).matches(); //|| Pattern.compile(FullName.regexNameEnglish).matcher(name).matches();
        } catch (NullPointerException e) {
            return false;
        }
    }

    public String getFullName() {
        return firstName + " " + secondName;
    }

    @Override
    public String toString() {
        return "FullName{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                '}';
    }
}