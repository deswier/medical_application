package com.myapplication.domain;

import lombok.Data;

@Data
public class User {
    //    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}