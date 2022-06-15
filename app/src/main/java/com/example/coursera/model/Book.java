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
    String pdf_url;

   

    protected Book(Parcel in) {
        id = in.readString();
        title = in.readString();
        image_url = in.readString();
        description = in.readString();
        author = in.readString();
        authorAnonim = in.readString();
        pdf_url = in.readString();
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

    public Book(String id,String title, String description, String image_url, String author, String authorAnonim, String pdf_url ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image_url = image_url;
        this.author =  author;
        this.authorAnonim = authorAnonim;
        this.pdf_url = pdf_url;
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

    public String getPdf_url() {
        return pdf_url;
    }

    public void setPdf_url(String pdf_url) {
        this.pdf_url = pdf_url;
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
        parcel.writeString(pdf_url);

    }
}
