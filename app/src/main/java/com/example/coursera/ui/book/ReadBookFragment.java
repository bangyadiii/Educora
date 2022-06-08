package com.example.coursera.ui.book;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.coursera.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ReadBookFragment extends Fragment {
   BottomNavigationView bottomNavigationView;

    public ReadBookFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_read_book, container, false);
        bottomNavigationView = view.findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnClickListener((View.OnClickListener) this);
        return view;
    }
}