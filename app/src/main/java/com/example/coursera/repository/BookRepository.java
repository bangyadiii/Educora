package com.example.coursera.repository;

import android.app.Application;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class BookRepository {
    public String BASE_COLLECTION = "Book";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    private CollectionReference bookRef = db.collection(BASE_COLLECTION);
    private CollectionReference bookRef1 = db.collection("Book");
    private Application app;


    public BookRepository(Application app) {
        this.app = app;
    }

    public void getBookImage(String image){
        StorageReference fotoRef = storageReference.child(image);

    }

    public Query getAllbooks(){
        return bookRef1.limit(10);
    }

//    public void getbook(String uid){
//        bookRef.whereEqualTo("user_id", "uid");
//    }

//    public Query getMateriBybook(String book_id){
//        return bookRef1.whereEqualTo("book_id", book_id);
//        return courseRef.document(course_id).collection("Materi");
//    }
}
