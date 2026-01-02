package com.example.perdelium.model;

import java.util.List;

public class ContentResponse {

    private boolean success;
    private List<Content> contents;

    // getter & setter
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public List<Content> getContents() { return contents; }
    public void setContents(List<Content> contents) { this.contents = contents; }
}
