package com.example.coursera.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentId;

public class Materi implements Parcelable {
    @DocumentId
    private String id;
    private String course_id;
    private String title;
    private String description;
    private String materi_video;



    public Materi(String id, String course_id, String title, String description, String materi_video) {
        this.id = id;
        this.course_id = course_id;
        this.title = title;
        this.description = description;
        this.materi_video = materi_video;
    }

    public Materi() {
    }

    protected Materi(Parcel in) {
        id = in.readString();
        course_id =  in.readString();
        title = in.readString();
        description = in.readString();
        materi_video = in.readString();
    }

    public static final Creator<Materi> CREATOR = new Creator<Materi>() {
        @Override
        public Materi createFromParcel(Parcel in) {
            return new Materi(in);
        }

        @Override
        public Materi[] newArray(int size) {
            return new Materi[size];
        }
    };


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
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

    public String getMateri_video() {
        return materi_video;
    }

    public void setMateri_video(String materi_video) {
        this.materi_video = materi_video;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(course_id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(materi_video);
    }
}
