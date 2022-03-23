package com.myapplication.model;

import com.myapplication.tools.DateParser;

public class TestProfile {
    Profile p;
    TestProfile() throws Exception {
        p=new Profile(new FullName("Alina Mikhaleva"), DateParser.parseStringToDate("2010-12-10"), 'F');
    }
//    void setImage(Image image){
//        p.setPhoto(image);
//    }
}
