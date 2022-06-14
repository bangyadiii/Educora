package com.example.coursera.ui.account;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.coursera.R;


import com.example.coursera.databinding.FragmentAccountBinding;
import com.example.coursera.model.Book;
import com.example.coursera.model.Course;
import com.example.coursera.model.User;
import com.example.coursera.ui.adapter.CourseAdapter;
import com.example.coursera.ui.adapter.SmallBookAdapter;
import com.example.coursera.ui.book.BookFragmentDirections;
import com.example.coursera.ui.book.BookViewModel;
import com.example.coursera.ui.helper.LoadingDialog;
import com.example.coursera.ui.helper.VerticalSpaceItemDecoration;
import com.example.coursera.ui.home.HomeViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;


public class AccountFragment extends Fragment {
    CourseAdapter progressCourseAdapter;
    HomeViewModel homeViewModel;
    private FragmentAccountBinding binding;
    BookViewModel dashboardViewModel;
    SmallBookAdapter bookAdapterGrid;
    FirebaseAuth mAuth;
    User user;
    Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(HomeViewModel.class);
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        dashboardViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        activity=  requireActivity();

        getCurrentUser();

        binding.changeProfile.setOnClickListener(view -> {
            LoadingDialog loadingDialog = LoadingDialog.getInstance(requireActivity());
            loadingDialog.startLoadingDialog();
            if(user != null) {
                NavDirections action = (NavDirections) AccountFragmentDirections.actionNavigationAccountToNavigationEditProfile(user);
                Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment_activity_main)).navigate(action);
            }
            loadingDialog.dissmisDialog();

        });

        binding.logout.setOnClickListener(view -> {
            Toast.makeText(getActivity() ,"Logout",Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            getActivity().finish();
        });
        setProgressCourseAdapter();
        setTrendingBookAdapter();

//        setBooksGridAdapter();
        return  binding.getRoot();
    }
    @Override
    public void onStart() {
        super.onStart();
        progressCourseAdapter.startListening();
        bookAdapterGrid.startListening();

    }
    @Override
    public void onStop() {
        super.onStop();
        progressCourseAdapter.stopListening();
        bookAdapterGrid.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }

    public void setProgressCourseAdapter(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                requireActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false
        );
        binding.rvCourse.setLayoutManager(layoutManager);
        binding.rvCourse.setItemAnimator(new DefaultItemAnimator());
        binding.rvCourse.addItemDecoration(new VerticalSpaceItemDecoration(20));

        FirestoreRecyclerOptions<Course> options = new FirestoreRecyclerOptions.Builder<Course>()
                .setQuery(homeViewModel.getAllCourses(), Course.class)
                .build();
        progressCourseAdapter = new CourseAdapter(options);
        progressCourseAdapter.setOnItemClickListener((view, position, model) -> {
            Log.d("model", model.toString());
            NavDirections action = (NavDirections) AccountFragmentDirections.actionNavigationAccountToDetailCourse(model.getId());
            Navigation.findNavController(requireActivity().findViewById(R.id.nav_host_fragment_activity_main)).navigate(action);
        });
        binding.rvCourse.setAdapter(progressCourseAdapter);

    }

    public void setTrendingBookAdapter(){
        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 3, RecyclerView.VERTICAL, false);
        binding.recyclerView2.setLayoutManager(layoutManager);
        binding.recyclerView2.setItemAnimator(new DefaultItemAnimator());
//        binding.recyclerView2.hasFixedSize();
        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(dashboardViewModel.getAllBook(), Book.class)
                .build();

        bookAdapterGrid = new SmallBookAdapter(options);
        bookAdapterGrid.setOnItemClickListener((view, position, model) -> {
            NavDirections action =  AccountFragmentDirections.actionNavigationAccountToBookDetailFragment(model);
            Navigation.findNavController(requireActivity().findViewById(R.id.nav_host_fragment_activity_main)).navigate(action);
        });
        binding.recyclerView2.setAdapter(bookAdapterGrid);
    }

    private void showAvatar(){
        if(user != null && user.getAvatar() != null) {
            StorageReference mStorageReference = FirebaseStorage.getInstance().getReference().child(user.getAvatar());
            try {
                final File localFile = File.createTempFile("book", "png");
                mStorageReference.getFile(localFile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inSampleSize = 8;
                                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath(), options);

                                Glide.with(activity)
                                        .load(bitmap)
                                        .override(120, 120)
                                        .centerCrop()
                                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                        .into(binding.avatar1);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireActivity(), "Error Occurred", Toast.LENGTH_SHORT).show();
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getCurrentUser(){
        mAuth  = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null) {
//            if (!queryDocumentSnapshots.isEmpty()) {
//                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
//                    user = snapshot.toObject(User.class);
//                    binding.accNama.setText(user.getName() != null ? user.getName() : "Default");
//                }
//            }
            FirebaseUser fUser = mAuth.getCurrentUser();
            FirebaseFirestore datau = FirebaseFirestore.getInstance();
            datau.collection("users").document(fUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        user = documentSnapshot.toObject(User.class);
                        binding.accNama.setText(user.getName() != null ? user.getName() : "Name unkown :(");
                        showAvatar();
                    }

                }
            });
        }

    }


}