package com.example.coursera.ui.book;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.coursera.R;

import com.example.coursera.databinding.FragmentReadBookBinding;
import com.example.coursera.model.Book;
import com.example.coursera.ui.helper.LoadingDialog;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;

import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class ReadBookFragment extends Fragment {
//    private BottomNavigationView bottomNavigationView;
    private FragmentReadBookBinding binding;
    private Book book;



    public ReadBookFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        book = getArguments().getParcelable("book");


        binding = FragmentReadBookBinding.inflate(inflater, container, false);
//            bottomNavigationView = binding.bottomNav;
//            bottomNavigationView.setOnClickListener((View.OnClickListener) this);

        loadBookFromURL();
        return binding.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();
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
                Toast.makeText(requireContext(), "error saat download pdf", Toast.LENGTH_SHORT).show();
                binding.pbBooks.setVisibility(View.GONE);
            }
        });
    }

    private void loadBookFromURL() {
        binding.pbBooks.setVisibility(View.VISIBLE);
        Log.d("url", book.getPdf_url());



    }

    private void setPDF(byte [] bytes) {
        Toast.makeText(requireActivity(), String.valueOf(bytes.length), Toast.LENGTH_SHORT).show();
        binding.pdfReader
                .fromBytes(bytes)
                .enableAntialiasing(true)
                .swipeHorizontal(false)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        Toast.makeText(requireContext(), "selesai memasang.", Toast.LENGTH_SHORT).show();
                        binding.pbBooks.setVisibility(View.GONE);
                    }
                })
                .onRender(new OnRenderListener() {
                    @Override
                    public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                        Toast.makeText(requireContext(), "rendered", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), "Error occur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        binding.pbBooks.setVisibility(View.GONE);
                    }
                }).onPageError(new OnPageErrorListener() {
                    @Override
                    public void onPageError(int page, Throwable t) {
                        Toast.makeText(getActivity(), "Error on page " + page, Toast.LENGTH_SHORT).show();
                    }
                })
                .password(null)
                .load();



    }
}