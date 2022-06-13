package com.example.coursera.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentId;

public class User implements Parcelable {
    @DocumentId
    private String uid;
    private String email;
    private String name;
    private String username;
    private String noHP;
    private String avatar;

    public User() {

    }

    public User(String uid, String email, String name, String username, String noHP, String avatar) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.username = username;
        this.noHP = noHP;
        this.avatar = avatar;
    }

    protected User(Parcel in) {
        uid = in.readString();
        email = in.readString();
        name = in.readString();
        username = in.readString();
        noHP = in.readString();
        avatar =in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getUid());
        parcel.writeString(getEmail());
        parcel.writeString(getName());
        parcel.writeString(getUsername());
        parcel.writeString(getNoHP());
        parcel.writeString(getAvatar());

    }
}
