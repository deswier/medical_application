package com.myapplication.storage;

import com.myapplication.exception.DataException;
import com.myapplication.model.Note;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

@Getter
public class TestNotes implements Serializable {
    ArrayList<Note> notes = new ArrayList<>();

    public TestNotes() throws DataException {
        addAll();
    }

    private void addAll() throws DataException {
        notes.add(new Note(UUID.randomUUID(), "Invitro",
                "Fe", Calendar.getInstance(), "9", "9-30.4", "mm/l", "null"));
        notes.add(new Note(UUID.randomUUID(), "Invitro",
                "HbA1c", Calendar.getInstance(), "7", "0 -6", "%", null));
        notes.add(new Note(UUID.randomUUID(), "Invitro",
                "HbA1c", Calendar.getInstance(), "7", "0-6-8", "%", null));
        notes.add(new Note(UUID.randomUUID(), "KDL",
                "HbA1c", Calendar.getInstance(), "5.9", "0-6", "%", null));
        notes.add(new Note(UUID.randomUUID(), "Invitro",
                "Fe", Calendar.getInstance(), "28.85", "9-30.4", "мкмоль/л", null));
        notes.add(new Note(UUID.randomUUID(), "Invitro",
                "Билирубин общий", Calendar.getInstance(), "14.3", "3.4-20.5", "мкмоль/л", null));
        notes.add(new Note(UUID.randomUUID(), "Invitro",
                "c-реактивный белок", Calendar.getInstance(), "выявленно", "не выявленно", "мкмоль/л", null));
        notes.add(new Note(UUID.randomUUID(), "Invitro",
                "c-реактивный белок", Calendar.getInstance(), "7", "не выявленно", "мкмоль/л", null));
        notes.add(new Note(UUID.randomUUID(), "Invitro",
                "c-реактивный белок", Calendar.getInstance(), "7", "не выявленно", "мкмоль/л", null));
        notes.add(new Note(UUID.randomUUID(), "Invitro",
                "c-реактивный белок", Calendar.getInstance(), "7", "не выявленно", "мкмоль/л", null));
        notes.add(new Note(UUID.randomUUID(), "Invitro",
                "c-реактивный белок", Calendar.getInstance(), "7", "не выявленно", "мкмоль/л", null));
    }

    public void add(Note n) throws DataException {
        notes.add(n);
    }

    public ArrayList<Note> searchNote(String findTest) {
        ArrayList<Note> res = new ArrayList();
        for (Note note : notes) {
            if (isSubstring(findTest, note.getTest())) res.add(note);
        }
        return res;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    private boolean isSubstring(String substr, String str) {
        String[] strArray = str.split(" ");
        for (String s : strArray) {
            if (s.length() >= substr.length())
                if (s.substring(0, substr.length()).toLowerCase(Locale.ROOT).equals(substr.toLowerCase(Locale.ROOT)))
                    return true;
        }
        return false;
    }
}