package com.example.coursera.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MyBusiness.MyApp.databinding.MateriItemBinding;
import com.example.coursera.model.Materi;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MateriAdapter extends FirestoreRecyclerAdapter<Materi, MateriAdapter.MateriItemHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MateriAdapter(@NonNull FirestoreRecyclerOptions<Materi> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MateriItemHolder holder, int position, @NonNull Materi model) {
        Log.e("adapter",model.toString());
        Log.d("adapter", String.valueOf(position));
        holder.getBinding().tvTitle.setText("hello World");
        holder.getBinding().tvDescriptionForMateri.setText("ini deskripsi");

    }

    @NonNull
    @Override
    public MateriItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MateriItemBinding binding = MateriItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new MateriItemHolder(binding);
    }

    public class MateriItemHolder extends RecyclerView.ViewHolder{
        MateriItemBinding binding;

        public MateriItemHolder(@NonNull MateriItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public MateriItemBinding getBinding() {
            return binding;
        }
    }
}
