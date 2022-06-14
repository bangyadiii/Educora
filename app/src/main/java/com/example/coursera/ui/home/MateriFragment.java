package com.example.coursera.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coursera.databinding.FragmentMateriBinding;
import com.example.coursera.model.Materi;
import com.example.coursera.ui.adapter.MateriAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MateriFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MateriFragment extends Fragment {
    FragmentMateriBinding binding;
    HomeViewModel homeViewModel;
    MateriAdapter adapter;


    public MateriFragment() {
        // Required empty public constructor
    }

    public static MateriFragment newInstance(String param1, String param2) {
        MateriFragment fragment = new MateriFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(this,
                (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication()))
                .get(HomeViewModel.class);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMateriBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setMateriAdapter();
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        setMateriAdapter();

    }



    public void setMateriAdapter(){
        String course_id = getArguments().getString("course_id");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        binding.rvMateri.setLayoutManager(layoutManager);
        binding.rvMateri.setItemAnimator(new DefaultItemAnimator());
        binding.rvMateri.setHasFixedSize(true);
        FirestoreRecyclerOptions<Materi> options = new FirestoreRecyclerOptions.Builder<Materi>()
                .setQuery(homeViewModel.getMateriByCourse(course_id), Materi.class)
                .build();

        adapter = new MateriAdapter(options);
        adapter.setCourse_id(course_id);

        binding.rvMateri.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.startListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}