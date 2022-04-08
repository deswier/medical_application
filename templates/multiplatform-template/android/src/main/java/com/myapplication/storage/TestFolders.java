package com.myapplication.storage;

import com.myapplication.exception.DataException;
import com.myapplication.model.Folder;
import com.myapplication.model.Profile;

import java.util.ArrayList;
import java.util.UUID;

public class TestFolders {
    public ArrayList<Folder> folders = new ArrayList<>();

    public TestFolders() throws DataException {
         addAll();
    }

    private void addAll() throws DataException {
        ArrayList<Folder> test= new ArrayList<>();
        test.add(new Folder("M"));
        folders.add(new Folder("УЗИ",test));
        folders.add(new Folder("ЭКГ"));
        folders.add(new Folder("Справки"));
    }
}
