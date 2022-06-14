package com.example.coursera.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.coursera.R;
import com.example.coursera.databinding.ProgressClassItemBinding;
import com.example.coursera.model.Course;
import com.example.coursera.ui.home.HomeFragmentDirections;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;


public class CourseAdapter extends FirestoreRecyclerAdapter<Course, CourseAdapter.CourseItemHolder> {
    AppCompatActivity context;


    public AppCompatActivity getContext() {
        return context;
    }

    public void setContext(AppCompatActivity context) {
        this.context = context;
    }
    public ClickListener listener;

    public void setOnItemClickListener(ClickListener listener){
        this.listener = listener;
    }

    public static interface ClickListener{
        void onItemClick(View view, int position, Course model);

    }



    public CourseAdapter(@NonNull FirestoreRecyclerOptions<Course> options) {
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
            listener.onItemClick(holder.getBinding().getRoot(), position, model);


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
            Log.d("course", course.getIcon_url());
            binding.tvCourseTitle.setText(course.getTitle());
            binding.tvDescription.setText(course.getDescription());
            StorageReference mStorageReference= FirebaseStorage.getInstance().getReference().child(course.getIcon_url());
            try{
//            String image_url;
                final File localFile = File.createTempFile("course_icon","png");
                mStorageReference.getFile(localFile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>(){
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot){
                                Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());

                                Glide.with(context)
                                        .load(bitmap)
                                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                        .into(binding.ivCourseIcon);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();
                            }
                        });
            }catch (IOException e) {
                e.printStackTrace();
            }

        }

        public ProgressClassItemBinding getBinding() {
            return binding;
        }

    }

}
