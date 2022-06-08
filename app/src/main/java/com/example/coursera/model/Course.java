package com.example.coursera.model;

import com.google.firebase.firestore.DocumentId;

public class Course {
    @DocumentId
    private String id;
    private String title;
    private String description;
    private String icon_url;

    public Course() {}

    public Course(String id, String title, String description, String icon_url) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.icon_url = icon_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }
}
