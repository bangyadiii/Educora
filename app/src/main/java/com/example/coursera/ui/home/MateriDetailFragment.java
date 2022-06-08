package com.example.coursera.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coursera.R;
import com.example.coursera.databinding.FragmentBookDetailBinding;
import com.example.coursera.databinding.FragmentMateriDetailBinding;


public class MateriDetailFragment extends Fragment {
    FragmentMateriDetailBinding binding;


    public MateriDetailFragment() {
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
        binding = FragmentMateriDetailBinding.inflate(inflater);
        return  binding.getRoot();
    }
}