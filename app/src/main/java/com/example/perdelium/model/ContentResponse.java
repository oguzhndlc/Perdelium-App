package com.example.perdelium.model;

import java.util.List;

public class ContentResponse {

    private boolean success;
    private List<Content> contents;
    private Content content;

    public List<Content> getContents() {
        return contents;
    }

    public Content getContent() {
        return content;
    }
}

