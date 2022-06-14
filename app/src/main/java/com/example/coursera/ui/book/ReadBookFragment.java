package com.example.coursera.ui.book;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.coursera.R;
import com.example.coursera.databinding.FragmentReadBookBinding;
import com.example.coursera.ui.helper.LoadingDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class ReadBookFragment extends Fragment {
    BottomNavigationView bottomNavigationView;
    FragmentReadBookBinding binding;

    public ReadBookFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            binding = FragmentReadBookBinding.inflate(inflater, container, false);
//            bottomNavigationView = binding.bottomNav;
//            bottomNavigationView.setOnClickListener((View.OnClickListener) this);
        return binding.getRoot();

        }catch (Exception e) {
            Log.d("inflate", "onCreateView", e);
        }
        return binding.getRoot();

    }
}