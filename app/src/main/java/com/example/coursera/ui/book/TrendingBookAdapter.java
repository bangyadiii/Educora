package com.example.coursera.ui.book;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coursera.R;
import com.example.coursera.databinding.RowItemGridBinding;
import com.example.coursera.model.Book;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class TrendingBookAdapter extends FirestoreRecyclerAdapter<Book, TrendingBookAdapter.ViewHolder> {
    AppCompatActivity context;


    public TrendingBookAdapter(@NonNull FirestoreRecyclerOptions<Book> options_grid){
        super(options_grid);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowItemGridBinding rowItemBinding = RowItemGridBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context = (AppCompatActivity) parent.getContext();
        return new ViewHolder(rowItemBinding);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Book model) {
//        holder.imageView.setImageResource(mainModels.get(position).gettitle());
        if(context == null){
            return;
        }
        holder.getBinding().textView3.setText(model.getTitle());


        StorageReference mStorageReference= FirebaseStorage.getInstance().getReference().child(model.getImage_url());
        try{
            final File localFile = File.createTempFile("book","png");
            mStorageReference.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>(){
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot){

                Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                ((ImageView) context.findViewById(R.id.image_view)).setImageBitmap(bitmap);
                Glide.with(context)
                        .load(bitmap)
                        .centerCrop()
                        .into(holder.getBinding().bookTumbSmall);
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
        holder.getBinding().bookItemSmall.setOnClickListener(view -> {
            NavDirections action =  BookFragmentDirections.actionNavigationBookToBookDetailFragment(model);
            Navigation.findNavController(context.findViewById(R.id.nav_host_fragment_activity_main)).navigate(action);

        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RowItemGridBinding binding;

        public ViewHolder(@NonNull RowItemGridBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public RowItemGridBinding getBinding() {
            return binding;
        }
    }
}