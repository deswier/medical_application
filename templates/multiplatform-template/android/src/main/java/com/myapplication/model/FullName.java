package com.myapplication.model;

import java.util.Locale;
import java.util.regex.Pattern;

public class FullName {
    String firstName;
    String secondName;
    private static final String regexName = ("[a-zA-Z]+ | [а-яА-Я]+");

    FullName(String firstName, String secondName) {
        if (isCorrectName(firstName) && isCorrectName(secondName))
            this.firstName = firstUpperCase(firstName);
        this.secondName = firstUpperCase(secondName);
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
}