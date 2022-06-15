package com.example.coursera.ui.book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.coursera.databinding.ActivityReadBookBinding;
import com.example.coursera.model.Book;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ReadBookActivity extends AppCompatActivity {
    ActivityReadBookBinding binding;
    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReadBookBinding.inflate(LayoutInflater.from(this));
        book = getIntent().getParcelableExtra("book");
        if(book != null) {

            StorageReference reference = FirebaseStorage.getInstance().getReference().child(book.getPdf_url());

            reference.getBytes(1280000).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    setPDF(bytes);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ReadBookActivity.this, "error saat download pdf", Toast.LENGTH_SHORT).show();
                    binding.pbBooks.setVisibility(View.GONE);
                }
            });
        }
        setContentView(binding.getRoot());
    }
    @Override
    public void onStart() {
        super.onStart();

    }

    private void loadBookFromURL() {
        binding.pbBooks.setVisibility(View.VISIBLE);
        Log.d("url", book.getPdf_url());



    }

    private void setPDF(byte [] bytes) {
        Toast.makeText(ReadBookActivity.this, String.valueOf(bytes.length), Toast.LENGTH_SHORT).show();
        binding.pdfReader
                .fromBytes(bytes)
                .enableAntialiasing(true)
                .swipeHorizontal(false)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        Toast.makeText(ReadBookActivity.this, "selesai memasang.", Toast.LENGTH_SHORT).show();
                        binding.pbBooks.setVisibility(View.GONE);
                    }
                })
                .onRender(new OnRenderListener() {
                    @Override
                    public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                        Toast.makeText(ReadBookActivity.this, "rendered", Toast.LENGTH_SHORT).show();
                        Log.d("reso", String.valueOf(pageWidth));
                        Log.d("reso", String.valueOf(pageHeight));
                    }
                })
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {

                        //binding.toolbarSubtitleTv.setText(currentPage+ "/"+ pageCount);
                    }
                }).onError(new OnErrorListener() {
                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(ReadBookActivity.this, "Error occur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        binding.pbBooks.setVisibility(View.GONE);
                    }
                }).onPageError(new OnPageErrorListener() {
                    @Override
                    public void onPageError(int page, Throwable t) {
                        Toast.makeText(ReadBookActivity.this, "Error on page " + page, Toast.LENGTH_SHORT).show();
                    }
                })
                .password(null)
                .load();



    }
}