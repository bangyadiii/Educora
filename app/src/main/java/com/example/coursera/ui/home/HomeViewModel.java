package com.example.coursera.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coursera.model.User;
import com.example.coursera.repository.CourseRepository;
import com.example.coursera.repository.UserReposity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class HomeViewModel extends AndroidViewModel {
    CourseRepository courseRepository;
    UserReposity userReposity;


    public HomeViewModel(@NonNull Application application) {
        super(application);
        courseRepository = new CourseRepository(application);
        userReposity = new UserReposity(application);
    }

    public Query getAllCourses(){
        return courseRepository.getAllCourses();
    }

    public void getCourse(String uid){
        courseRepository.getCourse(uid);
    }

    public Query getMateriByCourse(String course_id){
        return courseRepository.getMateriByCourse(course_id);
    }
    public Task<Void> editUser(User data){
        return userReposity.editUser(data);
    }


}