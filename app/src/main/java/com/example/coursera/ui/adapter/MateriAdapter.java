package com.example.coursera.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.example.coursera.R;
import com.example.coursera.databinding.MateriItemBinding;
import com.example.coursera.model.Materi;
import com.example.coursera.ui.home.MateriFragmentDirections;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MateriAdapter extends FirestoreRecyclerAdapter<Materi, MateriAdapter.MateriItemHolder> {


    AppCompatActivity context;
    public MateriAdapter(@NonNull FirestoreRecyclerOptions<Materi> options) {
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
    protected void onBindViewHolder(@NonNull MateriItemHolder holder, int position, @NonNull Materi model) {
        model.setCourse_id(course_id);
        holder.getBinding().tvTitle.setText(model.getTitle());
        holder.getBinding().tvDescriptionForMateri.setText(model.getDescription());

        holder.getBinding().cvMateri.setOnClickListener(view -> {

            NavDirections action = (NavDirections) MateriFragmentDirections.actionDetailCourseToDetailMateri( model, getCourse_id());
            Navigation.findNavController(context.findViewById(R.id.nav_host_fragment_activity_main)).navigate(action);

        });

        holder.getBinding().imageButton.setOnClickListener(view -> {

            NavDirections action = (NavDirections) MateriFragmentDirections.actionDetailCourseToDetailMateri( model, getCourse_id());
            Navigation.findNavController(context.findViewById(R.id.nav_host_fragment_activity_main)).navigate(action);

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
