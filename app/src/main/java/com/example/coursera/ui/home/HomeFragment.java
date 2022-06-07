package com.example.coursera.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.coursera.databinding.FragmentHomeBinding;
import com.example.coursera.model.Course;
import com.example.coursera.ui.helper.VerticalSpaceItemDecoration;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class HomeFragment extends Fragment {

    ProgressCourseAdapter progressCourseAdapter;

    private FragmentHomeBinding binding;
    HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        setProgressCourseAdapter();

            View root = binding.getRoot();
        return root;
    }
    @Override
    public void onStart() {
        super.onStart();
        progressCourseAdapter.startListening();

    }
    @Override
    public void onStop() {
        super.onStop();
        progressCourseAdapter.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




    public void setProgressCourseAdapter(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false
        );
        binding.progressClassRecyclerView.setLayoutManager(layoutManager);
        binding.progressClassRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.progressClassRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(20));

        FirestoreRecyclerOptions<Course> options = new FirestoreRecyclerOptions.Builder<Course>()
                .setQuery(homeViewModel.getAllCourses(), Course.class)
                .build();
        progressCourseAdapter = new ProgressCourseAdapter(options);
        binding.progressClassRecyclerView.setAdapter(progressCourseAdapter);

    }
}