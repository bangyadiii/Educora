package com.example.coursera.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.coursera.model.Course;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class CourseRepository {
    public String BASE_COLLECTION = "Course";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference courseRef = db.collection(BASE_COLLECTION);
    private CollectionReference courseRef1 = db.collection("Materi");
    private Application app;

    public CourseRepository(Application app) {
        this.app = app;
    }

    public Query getAllCourses(){
          return courseRef.limit(10);
    }

    public void getCourse(String uid){
         courseRef.whereEqualTo("user_id", "uid");
    }

    public Query getMateriByCourse(String course_id){
        return courseRef1.whereEqualTo("course_id", course_id);
//        return courseRef.document(course_id).collection("Materi");
    }


}
