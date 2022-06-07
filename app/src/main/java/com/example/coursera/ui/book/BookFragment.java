package com.example.coursera.ui.book;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursera.databinding.FragmentBookBinding;
import com.example.coursera.model.Book;
import com.example.coursera.ui.home.ProgressCourseAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class BookFragment extends Fragment {

    private FragmentBookBinding binding;
    BookAdapter mainAdapter;
    BookViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(BookViewModel.class);

        binding = FragmentBookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //      code recyclerview dari MainActivity-------------------------------------
//        firebaseFirestore = FirebaseFirestore.getInstance();

//        //Query
//        Query query = firebaseFirestore.collection("Book");
//
//        //RecyclerOption
//        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>().setQuery(query, Book.class).build();
//
//        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<Book, BooksViewHolder>(options) {
//            @NonNull
//            @Override
//            public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return null;
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull BooksViewHolder holder, int position, @NonNull Book model) {
//
//            }
//        }

//        Integer[] langLogo = {R.drawable.book6,R.drawable.book2, R.drawable.book6};
//
//        String[] langName = {"Buku Fiksi", "Buku Non Fiksi", "Majalah"};
//
//        mainModels = new ArrayList<>();
//        for (int i=0; i<langLogo.length; i++){
//            Book model = new Book(langLogo[i], langName[i]);
//            mainModels.add(model);
//        }
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(
//                container.getContext(),LinearLayoutManager.HORIZONTAL,false
//        );
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        mainAdapter = new BookAdapter(getContext(), mainModels);
//
//        recyclerView.setAdapter(mainAdapter);

        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBooksAdapter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    private class BooksViewHolder extends RecyclerView.ViewHolder{
//        public BooksViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }

    @Override
    public void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

    public void setBooksAdapter() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false
        );
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.hasFixedSize();
        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(dashboardViewModel.getAllBook(), Book.class)
                .build();

        mainAdapter = new BookAdapter(options);

        binding.recyclerView.setAdapter(mainAdapter);

    }
}