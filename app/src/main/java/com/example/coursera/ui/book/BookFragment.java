package com.example.coursera.ui.book;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursera.R;
import com.example.coursera.databinding.FragmentBookBinding;
import com.example.coursera.model.Book;
import com.example.coursera.ui.adapter.LargeBookAdapter;
import com.example.coursera.ui.adapter.SmallBookAdapter;
import com.example.coursera.ui.helper.SpaceItemDecoration;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class BookFragment extends Fragment {

    private FragmentBookBinding binding;
    private LargeBookAdapter largeBookAdapter;
    private SmallBookAdapter trendingBookAdapter;
    BookViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(BookViewModel.class);

        binding = FragmentBookBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecomendedBookAdapter();
        setTrendingBookAdapter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onStart() {
        super.onStart();
        largeBookAdapter.startListening();
        trendingBookAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        largeBookAdapter.stopListening();
        trendingBookAdapter.stopListening();
    }

    public void setTrendingBookAdapter(){
        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 3, RecyclerView.VERTICAL, false);
        binding.rvTrendingBook.setLayoutManager(layoutManager);
        binding.rvTrendingBook.setItemAnimator(new DefaultItemAnimator());
//        binding.rvTrendingBook.hasFixedSize();
        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(dashboardViewModel.getAllBook(), Book.class)
                .build();

        trendingBookAdapter = new SmallBookAdapter(options);
        trendingBookAdapter.setOnItemClickListener((view, position, model) -> {
            NavDirections action =  BookFragmentDirections.actionNavigationBookToBookDetailFragment(model);
            Navigation.findNavController(requireActivity().findViewById(R.id.nav_host_fragment_activity_main)).navigate(action);
        });
        binding.rvTrendingBook.setAdapter(trendingBookAdapter);
    }

    public void setRecomendedBookAdapter() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                requireActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false
        );
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
//        binding.recyclerView.hasFixedSize();
        binding.recyclerView.addItemDecoration(new SpaceItemDecoration(40, 10));
        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(dashboardViewModel.getAllBook(), Book.class)
                .build();

        largeBookAdapter = new LargeBookAdapter(options);
        largeBookAdapter.setOnItemClickListener((view, position, model) -> {
            NavDirections action =  BookFragmentDirections.actionNavigationBookToBookDetailFragment(model);
            Navigation.findNavController(requireActivity().findViewById(R.id.nav_host_fragment_activity_main)).navigate(action);
        });

        binding.recyclerView.setAdapter(largeBookAdapter);

    }
}