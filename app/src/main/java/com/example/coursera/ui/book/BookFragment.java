package com.example.coursera.ui.book;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.MyBusiness.MyApp.R;
import com.MyBusiness.MyApp.databinding.FragmentBookBinding;
import com.example.coursera.model.Book;

import java.util.ArrayList;


public class BookFragment extends Fragment {

    private FragmentBookBinding binding;
    RecyclerView recyclerView;
    ArrayList<Book> mainModels;
    BookAdapter mainAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        BookViewModel dashboardViewModel =
                new ViewModelProvider(this).get(BookViewModel.class);

        binding = FragmentBookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //      code recyclerview dari MainActivity-------------------------------------
        recyclerView = binding.recyclerView;

        Integer[] langLogo = {R.drawable.book6,R.drawable.book2, R.drawable.book6};

        String[] langName = {"Buku Fiksi", "Buku Non Fiksi", "Majalah"};

        mainModels = new ArrayList<>();
        for (int i=0; i<langLogo.length; i++){
            Book model = new Book(langLogo[i], langName[i]);
            mainModels.add(model);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                container.getContext(),LinearLayoutManager.HORIZONTAL,false
        );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mainAdapter = new BookAdapter(getContext(), mainModels);

        recyclerView.setAdapter(mainAdapter);

        return root;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}