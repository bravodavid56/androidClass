package com.example.bravodavid56.newsapp;

import android.app.Application;

/**
 * Created by bravodavid56 on 6/26/2017.
 */

public class NewsItem extends Application {
    private String authorName;
    private String title;
    private String description;
    private String url;
    private String time;

    public NewsItem(String authorName, String title, String description, String url, String time) {
        this.authorName = authorName;
        this.title = title;
        this.description = description;
        this.url = url;
        this.time = time;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
