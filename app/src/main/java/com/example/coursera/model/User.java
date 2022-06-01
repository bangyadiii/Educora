package com.example.coursera.model;

public class User {
    private  String uid;
    private String email;
    private String name;
    private String username;
    private String noHP;

    public User() {

    }

    public User(String uid, String email, String name, String username, String noHP) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.username = username;
        this.noHP = noHP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNoHP() {
        return noHP;
    }

    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
