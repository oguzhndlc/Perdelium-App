package com.example.perdelium.model;

public class LoginRequest {

    private String identifier;  // email veya username

    private String password;

    // Parametresiz constructor
    public LoginRequest() {}

    // Constructor ile değer atama
    public LoginRequest(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }

    // Getter ve Setter metodları
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
