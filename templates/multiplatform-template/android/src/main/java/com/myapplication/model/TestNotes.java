package com.myapplication.model;

import android.os.Build;
import lombok.Data;
import lombok.Getter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class TestNotes {
    ArrayList<Note> notes= new ArrayList();
    public TestNotes(){
        notes.add(new Note(UUID.randomUUID(),"Invitro",new FullName("Alina","Mikhaleva"),
                "Fe",Date.valueOf("25.02.2021"),"9","9-30.4", "мкмоль/л",null));
        notes.add(new Note(UUID.randomUUID(),"Invitro",new FullName("Alina","Mikhaleva"),
                "HbA1c",Date.valueOf("04.03.2022"),"5.5","0-6", "%",null));
        notes.add(new Note(UUID.randomUUID(),"KDL",new FullName("Alina","Mikhaleva"),
                "HbA1c",Date.valueOf("09.02.2020"),"5.9","0-6", "%",null));
        notes.add(new Note(UUID.randomUUID(),"Invitro",new FullName("Alina","Mikhaleva"),
                "Fe",Date.valueOf("09.02.2020"),"28.85","9-30.4", "мкмоль/л",null));
//        notes.add(new Note(UUID.randomUUID(),"Invitro",new FullName("Alina","Mikhaleva"),
//                "Билирубин общий",Date.valueOf("09.02.2020"),"14.3","3.4-20.5", "мкмоль/л",null));
    }

    public ArrayList<Note> findNote(String lab){
        ArrayList<Note> res= new ArrayList();
        for (Note note : notes) {
            if (Objects.equals(note.getLab(), lab)) res.add(note);
        }
        return res;
    }
}
