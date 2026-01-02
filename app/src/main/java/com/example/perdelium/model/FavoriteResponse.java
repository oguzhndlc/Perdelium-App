package com.example.perdelium.model;

import java.util.List;

public class FavoriteResponse {

    private boolean success;
    private List<Content> favorites; // Content modelini kullanıyoruz

    // getter & setter
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public List<Content> getFavorites() { return favorites; }
    public void setFavorites(List<Content> favorites) { this.favorites = favorites; }
}
