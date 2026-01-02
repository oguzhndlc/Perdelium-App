package com.example.perdelium.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Content {
    private int id;
    private String title;
    private String explanation;
    private String html_content;
    private String created_at;
    private String updated_at;
    private String deleted_at;
    private String user_id;
    private Users users;  // Kullanıcı bilgileri
    private List<ContentTag> content_tags;  // content_tags dizisi

    // Getter ve Setter metodları
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getHtml_content() {
        return html_content;
    }

    public void setHtml_content(String html_content) {
        this.html_content = html_content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public List<ContentTag> getContent_tags() {
        return content_tags;
    }

    public void setContent_tags(List<ContentTag> content_tags) {
        this.content_tags = content_tags;
    }
}
