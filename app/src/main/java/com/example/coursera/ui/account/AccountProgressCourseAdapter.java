package com.example.coursera.ui.account;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursera.R;
import com.example.coursera.databinding.ProgressClassItemBinding;
import com.example.coursera.model.Course;
import com.example.coursera.ui.home.HomeFragmentDirections;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class AccountProgressCourseAdapter extends FirestoreRecyclerAdapter<Course, AccountProgressCourseAdapter.CourseItemHolder> {
    AppCompatActivity context;

    public AccountProgressCourseAdapter(@NonNull FirestoreRecyclerOptions<Course> options) {
        super(options);
    }


    @NonNull
    @Override
    public CourseItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProgressClassItemBinding binding = ProgressClassItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = (AppCompatActivity) parent.getContext();

        binding.cvProgressItem.setOnClickListener(view -> {
//            NavOptions navOptions = new NavOptions.Builder()
//                    .setPopUpTo(R.id.detail_course, true)
//                    .build();
//
//                 Navigation.findNavController(context.findViewById(R.id.nav_host_fragment_activity_main))
//                         .navigate(R.id.action_navigation_home_to_detail_course);

        });

        return new CourseItemHolder(binding, parent.getContext());

    }

    @Override
    protected void onBindViewHolder(@NonNull CourseItemHolder holder, int position, @NonNull Course model) {
        holder.bind(model);
        holder.getBinding().cvProgressItem.setOnClickListener(v -> {
//            NavOptions navOptions = new NavOptions.Builder()
//                    .setPopUpTo(R.id.navigation_home, true)
//                    .build();
            Log.d("model", model.toString());
            NavDirections action = (NavDirections) AccountFragmentDirections.actionNavigationAccountToDetailCourse(model.getId());
            Navigation.findNavController(context.findViewById(R.id.nav_host_fragment_activity_main)).navigate(action);
            //Navigation.findNavController(context.findViewById(R.id.nav_host_fragment_activity_main)).navigate(R.id.action_navigation_home_to_detail_course);

        });
    }



    public class CourseItemHolder extends RecyclerView.ViewHolder{
        ProgressClassItemBinding binding;
        Course course;
        Context context;

        public CourseItemHolder(@NonNull ProgressClassItemBinding binding, Context context) {
            super(binding.getRoot());
            this.binding = binding;
            this.context= context;

        }
        public void bind(Course course){
            this.course = course;
            binding.tvCourseTitle.setText(course.getTitle());
            binding.tvDescription.setText(course.getDescription());


        }

        public ProgressClassItemBinding getBinding() {
            return binding;
        }

    }

}
