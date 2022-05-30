package com.example.coursera.ui.book;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.coursera.repository.BookRepository;
import com.google.firebase.firestore.Query;

public class BookViewModel extends AndroidViewModel {

    BookRepository bookRepository;


    public BookViewModel(@NonNull Application application) {
        super(application);
        bookRepository = new BookRepository(application);
    }

    public Query getAllBook(){
        return bookRepository.getAllbooks();
    }


}