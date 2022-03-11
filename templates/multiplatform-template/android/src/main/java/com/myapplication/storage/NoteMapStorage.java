package com.myapplication.storage;

import com.myapplication.model.Note;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NoteMapStorage {
    private Map<UUID, Note> map = new HashMap<>();

    public  void put(UUID uuid, Note note){
        map.put(uuid,note);
    }

    public  void delete(UUID uuid){
        map.remove(uuid);
    }
}
