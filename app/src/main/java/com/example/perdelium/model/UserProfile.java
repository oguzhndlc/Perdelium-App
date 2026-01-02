package com.example.perdelium.model;

import com.google.gson.annotations.SerializedName;

public class UserProfile {

    private String about;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("insta_link")
    private String instaLink;

    @SerializedName("web_link")
    private String webLink;

    private String phone;

    @SerializedName("updated_at")
    private String updatedAt;

    // getter & setter
    public String getAbout() { return about; }
    public void setAbout(String about) { this.about = about; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getInstaLink() { return instaLink; }
    public void setInstaLink(String instaLink) { this.instaLink = instaLink; }

    public String getWebLink() { return webLink; }
    public void setWebLink(String webLink) { this.webLink = webLink; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
