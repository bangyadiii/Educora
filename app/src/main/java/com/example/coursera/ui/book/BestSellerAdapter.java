package com.example.coursera.ui.book;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursera.databinding.RowItemGridBinding;
import com.example.coursera.model.Book;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class BestSellerAdapter extends FirestoreRecyclerAdapter<Book, BestSellerAdapter.GridViewHolder> {


    public BestSellerAdapter(@NonNull FirestoreRecyclerOptions<Book> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull GridViewHolder holder, int position, @NonNull Book model) {
        holder.binding.textView3.setText(model.getTitle());

    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowItemGridBinding binding = RowItemGridBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new BestSellerAdapter.GridViewHolder(binding);
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        RowItemGridBinding binding;
        public GridViewHolder(@NonNull RowItemGridBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
