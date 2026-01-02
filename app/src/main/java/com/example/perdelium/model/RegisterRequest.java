package com.example.perdelium.model;

public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private String name;
    private String surname;

    public RegisterRequest(String username, String email, String password, String name, String surname) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }
}

