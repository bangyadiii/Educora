package com.example.coursera.ui.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.coursera.R;

import com.example.coursera.databinding.RowItemBinding;
import com.example.coursera.model.Book;
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

public class LargeBookAdapter extends FirestoreRecyclerAdapter<Book, LargeBookAdapter.GridViewHolder> {
//    ArrayList<Book> mainModels;
    Activity act;

    public LargeBookAdapter(@NonNull FirestoreRecyclerOptions<Book> options){
        super(options);
    }

    public ClickListener listener;

    public void setOnItemClickListener(ClickListener listener){
        this.listener = listener;
    }

    public interface ClickListener{
        void onItemClick(View view, int position, Book model);

    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        act = (Activity) parent.getContext();

        RowItemBinding rowItemBinding = RowItemBinding.inflate(LayoutInflater.from(act),parent,false);
        return new LargeBookAdapter.GridViewHolder(rowItemBinding);
    }

    @Override
    protected void onBindViewHolder(@NonNull GridViewHolder holder, int position, @NonNull Book model) {
        StorageReference mStorageReference= FirebaseStorage.getInstance().getReference().child(model.getImage_url());
        try{

            final File localFile = File.createTempFile("book","png");
            mStorageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>(){
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot){

                            Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());

                            Glide.with(act).load(bitmap)
                                    .centerCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                    .into(holder.getRowItemGridBinding().imageView);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(act, "Error Occurred", Toast.LENGTH_SHORT).show();
                        }
                    });
        }catch (IOException e) {
            e.printStackTrace();
        }
        holder.getRowItemGridBinding().textView1.setText(model.getTitle());
        holder.getRowItemGridBinding().cvBookItemLarge.setOnClickListener(view -> {
            listener.onItemClick(view, position, model);

        });

    }




    public class GridViewHolder extends RecyclerView.ViewHolder {
        RowItemBinding binding;

        public GridViewHolder(@NonNull RowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public RowItemBinding getRowItemGridBinding() {
            return binding;
        }
    }
}
