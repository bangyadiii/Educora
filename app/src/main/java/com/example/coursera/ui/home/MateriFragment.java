package com.example.coursera.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.MyBusiness.MyApp.R;
import com.MyBusiness.MyApp.databinding.FragmentHomeBinding;
import com.MyBusiness.MyApp.databinding.FragmentMateriBinding;
import com.example.coursera.model.Course;
import com.example.coursera.model.Materi;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMateriBinding.inflate(inflater, container, false);
        setMateriAdapter();
        return inflater.inflate(R.layout.fragment_materi, container, false);

    }

    public void setMateriAdapter(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false
        );
        binding.rvMateri.setLayoutManager(layoutManager);
        binding.rvMateri.setItemAnimator(new DefaultItemAnimator());
        binding.rvMateri.hasFixedSize();
        FirestoreRecyclerOptions<Materi> options = new FirestoreRecyclerOptions.Builder<Materi>()
                .setQuery(homeViewModel.getMateriByCourse("4G1u2hu1gpmU7QabKkUf"), Materi.class)
                .build();

        adapter = new MateriAdapter(options);

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
        adapter.stopListening();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}