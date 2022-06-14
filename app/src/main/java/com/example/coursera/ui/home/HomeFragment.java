package com.example.coursera.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.coursera.R;
import com.example.coursera.databinding.FragmentHomeBinding;
import com.example.coursera.model.Course;
import com.example.coursera.ui.adapter.CourseAdapter;
import com.example.coursera.ui.helper.VerticalSpaceItemDecoration;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class HomeFragment extends Fragment {

    CourseAdapter courseAdapter;

    private FragmentHomeBinding binding;
    HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        setProgressCourseAdapter();

        return binding.getRoot();
    }
    @Override
    public void onStart() {
        super.onStart();
        courseAdapter.startListening();

    }
    @Override
    public void onStop() {
        super.onStop();
        courseAdapter.stopListening();
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
        courseAdapter = new CourseAdapter(options);
        courseAdapter.setOnItemClickListener((view, position, model) -> {
            Log.d("model", model.toString());
            NavDirections action = (NavDirections) HomeFragmentDirections.actionNavigationHomeToDetailCourse(model.getId());
            Navigation.findNavController(((AppCompatActivity) getActivity()).findViewById(R.id.nav_host_fragment_activity_main)).navigate(action);
        });
        binding.progressClassRecyclerView.setAdapter(courseAdapter);

    }
}