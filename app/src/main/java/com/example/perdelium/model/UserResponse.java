package com.example.perdelium.model;

import java.util.List;

public class UserResponse {

    private boolean success;
    private User user;
    private List<Content> contents;

    // getter & setter
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<Content> getContents() { return contents; }
    public void setContents(List<Content> contents) { this.contents = contents; }
}
