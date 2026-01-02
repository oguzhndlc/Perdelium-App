package com.example.perdelium.model;

import com.google.gson.annotations.SerializedName;

public class ContentTag {
    private String age_limit;
    private int cast_count;
    private int female_cast_count;
    private int male_cast_count;
    private String theme;
    private String time;
    private String type;

    // Getter ve Setter metodları
    public String getAge_limit() {
        return age_limit;
    }

    public void setAge_limit(String age_limit) {
        this.age_limit = age_limit;
    }

    public int getCast_count() {
        return cast_count;
    }

    public void setCast_count(int cast_count) {
        this.cast_count = cast_count;
    }

    public int getFemale_cast_count() {
        return female_cast_count;
    }

    public void setFemale_cast_count(int female_cast_count) {
        this.female_cast_count = female_cast_count;
    }

    public int getMale_cast_count() {
        return male_cast_count;
    }

    public void setMale_cast_count(int male_cast_count) {
        this.male_cast_count = male_cast_count;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
