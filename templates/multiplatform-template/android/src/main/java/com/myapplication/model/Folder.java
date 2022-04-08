package com.myapplication.model;

import java.io.File;
import java.util.ArrayList;

public class Folder {
    public String name;
    public ArrayList<Folder> folders = new ArrayList<>();
    public ArrayList<File> files = new ArrayList<>();

    public Folder(String name, ArrayList<Folder> folders, ArrayList<File> files) {
        this.name = name;
        this.folders = folders;
        this.files = files;
    }

    public Folder(String name, ArrayList<Folder> folders) {
        this.name = name;
        this.folders = folders;
    }

    public Folder(String name) {
        this.name = name;
        this.folders = null;
        this.files = null;
    }
}
