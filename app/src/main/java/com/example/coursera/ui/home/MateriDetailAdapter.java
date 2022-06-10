package com.example.coursera.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursera.R;
import com.example.coursera.databinding.MateriItemBinding;
import com.example.coursera.model.Materi;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MateriDetailAdapter extends FirestoreRecyclerAdapter<Materi, MateriDetailAdapter.MateriItemHolder> {
    AppCompatActivity context;


    public AppCompatActivity getContext() {
        return context;
    }

    public void setContext(AppCompatActivity context) {
        this.context = context;
    }



    public MateriDetailAdapter(@NonNull FirestoreRecyclerOptions<Materi> options) {
        super(options);
    }
    private String course_id;

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();


    }

    @Override
    protected void onBindViewHolder(@NonNull MateriDetailAdapter.MateriItemHolder holder, int position, @NonNull Materi model) {
        Log.e("adapter",model.toString());
        Log.d("adapter", String.valueOf(position));
        holder.getBinding().tvTitle.setText(model.getTitle());
        holder.getBinding().tvDescriptionForMateri.setText(model.getDescription());
//        holder.getBinding().cvMateri.setOnClickListener(view -> {
//
//            NavDirections action = (NavDirections) MateriFragmentDirections.actionDetailCourseToDetailMateri(model.getId(), model.getTitle(), model.getDescription(), getCourse_id());
//
//            Navigation.findNavController(context.findViewById(R.id.nav_host_fragment_activity_main)).navigate(action);
//
//        });

    }

    @NonNull
    @Override
    public MateriDetailAdapter.MateriItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MateriItemBinding binding = MateriItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = (AppCompatActivity ) parent.getContext();
        return new MateriDetailAdapter.MateriItemHolder(binding);
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
