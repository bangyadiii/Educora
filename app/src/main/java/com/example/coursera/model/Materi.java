package com.example.coursera.model;

public class Materi {
    private String id;
    private String title;
    private String description;


    public Materi(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Materi() {
    }

    public String getUid() {
        return id;
    }

    public void setUid(String uid) {
        this.id = uid;
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
}
