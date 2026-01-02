package com.example.perdelium.model;

import com.google.gson.annotations.SerializedName;

public class AddContentRequest {

    private String title;
    private String explanation;

    @SerializedName("html_content")
    private String htmlContent;

    private String type;
    private String theme;
    private String time;

    @SerializedName("age_limit")
    private String ageLimit;

    @SerializedName("cast_count")
    private int castCount;

    @SerializedName("male_cast_count")
    private int maleCastCount;

    @SerializedName("female_cast_count")
    private int femaleCastCount;

    public AddContentRequest(String title,
                             String explanation,
                             String htmlContent,
                             String type,
                             String theme,
                             String time,
                             String ageLimit,
                             int castCount,
                             int maleCastCount,
                             int femaleCastCount) {

        this.title = title;
        this.explanation = explanation;
        this.htmlContent = htmlContent;
        this.type = type;
        this.theme = theme;
        this.time = time;
        this.ageLimit = ageLimit;
        this.castCount = castCount;
        this.maleCastCount = maleCastCount;
        this.femaleCastCount = femaleCastCount;
    }
}
