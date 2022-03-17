package com.myapplication.model;

import com.myapplication.exception.DataException;

import java.io.Serializable;
import java.util.Locale;
import java.util.regex.Pattern;

public class FullName implements Serializable {
    String firstName;
    String secondName;
    private static final String regexName = ("([А-Я]{1}[а-яё]{1,23}|[A-Z]{1}[a-z]{1,23})");

    public FullName(String firstName, String secondName) throws DataException {
        if (isCorrectName(firstName) && isCorrectName(secondName)) {
            this.firstName = firstUpperCase(firstName);
            this.secondName = firstUpperCase(secondName);
        }
        else throw new DataException("Name is invalid "+firstName+" "+secondName);
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
            return Pattern.compile(FullName.regexName).matcher(name).matches();
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