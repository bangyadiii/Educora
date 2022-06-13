package com.example.coursera.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentId;

public class Book implements Parcelable {
    @DocumentId
    String id;
    String title;
    String description;
    String image_url;
    String author;
    String authorAnonim;

   

    protected Book(Parcel in) {
        id = in.readString();
        title = in.readString();
        image_url = in.readString();
        description = in.readString();
        author = in.readString();
        authorAnonim = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


    public Book(){}

    public Book(String id,String title, String description, String image_url) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image_url = image_url;
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

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorAnonim() {
        return authorAnonim;
    }

    public void setAuthorAnonim(String authorAnonim) {
        this.authorAnonim = authorAnonim;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(image_url);
        parcel.writeString(description);
        parcel.writeString(author);
        parcel.writeString(authorAnonim);

    }
}
