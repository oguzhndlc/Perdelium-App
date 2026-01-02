package com.example.perdelium.model;

import com.google.gson.annotations.SerializedName;

public class User {

    private String id;
    private String username;
    private String name;
    private String surname;
    private String email;
    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("user_profiles")
    private UserProfile profile; // Nested JSON

    // getter & setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public UserProfile getProfile() { return profile; }
    public void setProfile(UserProfile profile) { this.profile = profile; }
}
