package com.example.coursera.model;

public class Book {
    Integer langLogo;
    String langName;

    public Book(Integer langLogo, String langName){
        this.langLogo = langLogo;
        this.langName = langName;
    }

    public Integer getLangLogo(){
        return  langLogo;
    }

    public String getLangName(){
        return langName;
    }
}
