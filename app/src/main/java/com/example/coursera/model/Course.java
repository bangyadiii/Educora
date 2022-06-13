package com.example.coursera.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentId;

public class Course implements Parcelable {
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

    protected Course(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        icon_url = in.readString();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(icon_url);
    }
}
