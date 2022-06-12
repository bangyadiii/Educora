package com.example.coursera.repository;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;

import com.example.coursera.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class UserReposity {
    public String BASE_COLLECTION = "users";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection(BASE_COLLECTION);
    private Application app;



    public UserReposity(Application app) {
        this.app = app;
    }

    public Task<Void> editUser(User user){
       return userRef.document(user.getUid()).set(user);

    }




}
