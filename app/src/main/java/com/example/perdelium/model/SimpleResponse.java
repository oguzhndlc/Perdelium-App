package com.example.perdelium.model;

public class SimpleResponse {

    private boolean success;
    private String message;

    // Getter ve Setter'lar
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
