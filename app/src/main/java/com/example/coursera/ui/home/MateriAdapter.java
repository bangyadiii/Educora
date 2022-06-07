package com.example.coursera.ui.home;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.example.coursera.R;
import com.example.coursera.databinding.MateriItemBinding;
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
    AppCompatActivity context;
    public MateriAdapter(@NonNull FirestoreRecyclerOptions<Materi> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MateriItemHolder holder, int position, @NonNull Materi model) {
        Log.e("adapter",model.toString());
        Log.d("adapter", String.valueOf(position));
        holder.getBinding().tvTitle.setText(model.getTitle());
        holder.getBinding().tvDescriptionForMateri.setText(model.getDescription());
        holder.getBinding().cvMateri.setOnClickListener(view -> {
            Navigation.findNavController(context.findViewById(R.id.nav_host_fragment_activity_main)).navigate(R.id.action_detail_course_to_detail_materi);

        });

    }

    @NonNull
    @Override
    public MateriItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MateriItemBinding binding = MateriItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = (AppCompatActivity ) parent.getContext();
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
