package com.example.coursera.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.coursera.databinding.RowItemGridBinding;
import com.example.coursera.model.Book;
import com.example.coursera.model.Course;
import com.example.coursera.ui.book.BookFragmentDirections;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class SmallBookAdapter extends FirestoreRecyclerAdapter<Book, SmallBookAdapter.ViewHolder> {
    AppCompatActivity context;
    public ClickListener listener;

    public void setOnItemClickListener(ClickListener listener){
        this.listener = listener;
    }

    public interface ClickListener{
        void onItemClick(View view, int position, Book model);

    }

    public SmallBookAdapter(@NonNull FirestoreRecyclerOptions<Book> options_grid){
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
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
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
            listener.onItemClick(holder.getBinding().getRoot(), position, model);

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
