package com.example.coursera.ui.book;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.coursera.R;
import com.example.coursera.databinding.FragmentBookDetailBinding;
import com.example.coursera.model.Book;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookDetailFragment extends Fragment {
    private Book book;
    private SmallBookAdapter trendingBookAdapter;
    private FragmentBookDetailBinding binding;
    BookViewModel dashboardViewModel;

    public BookDetailFragment() {
        // Required empty public constructor
    }

    public static BookDetailFragment newInstance(String param1, String param2) {
        BookDetailFragment fragment = new BookDetailFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        binding = FragmentBookDetailBinding.inflate(inflater, container,  false);
        if(getArguments() != null){
            book = getArguments().getParcelable("book");
            StorageReference mStorageReference= FirebaseStorage.getInstance().getReference().child(book.getImage_url());

            try{
                final File localFile = File.createTempFile("book","png");
                mStorageReference.getFile(localFile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>(){
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot){

                                Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());

                                Glide.with(requireActivity())
                                        .load(bitmap)
                                        .fitCenter()
                                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                        .into(binding.imgBook1);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireActivity(), "Error Occurred", Toast.LENGTH_SHORT).show();
                            }
                        });
            }catch (IOException e) {
                e.printStackTrace();
            }

            binding.tvTitle.setText(book.getTitle());
            binding.tvAuthor.setText(book.getAuthor() != null ? book.getAuthor() : "Gatau" );
            binding.tvAuthorAnonim.setText(book.getAuthor() != null ? book.getAuthorAnonim() : "Juga gatau" );
            binding.btn1.setOnClickListener(view -> {
                NavDirections action = BookDetailFragmentDirections.actionBookDetailFragmentToReadBookFragment(book);
                Navigation.findNavController(requireActivity().findViewById(R.id.nav_host_fragment_activity_main)).navigate(action);
            });
        }



        setTrendingBookAdapter();

        return binding.getRoot();
    }

    public void setTrendingBookAdapter(){
        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 3, RecyclerView.VERTICAL, false);
        binding.rvDetailbook.setLayoutManager(layoutManager);
        binding.rvDetailbook.setItemAnimator(new DefaultItemAnimator());
//        binding.rvTrendingBook.hasFixedSize();
        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(dashboardViewModel.getAllBook(), Book.class)
                .build();

        trendingBookAdapter = new SmallBookAdapter(options);
        binding.rvDetailbook.setAdapter(trendingBookAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        trendingBookAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        trendingBookAdapter.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}