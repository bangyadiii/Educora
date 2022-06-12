package com.example.coursera.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.coursera.R;


import com.example.coursera.databinding.FragmentAccountBinding;
import com.example.coursera.model.Book;
import com.example.coursera.model.Course;
import com.example.coursera.model.User;
import com.example.coursera.ui.book.BookAdapterGrid;
import com.example.coursera.ui.book.BookViewModel;
import com.example.coursera.ui.helper.VerticalSpaceItemDecoration;
import com.example.coursera.ui.home.HomeViewModel;
import com.example.coursera.ui.home.MateriAdapter;
import com.example.coursera.ui.account.AccountProgressCourseAdapter;
import com.example.coursera.ui.home.ProgressCourseAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;


public class AccountFragment extends Fragment {
    AccountProgressCourseAdapter progressCourseAdapter;
    HomeViewModel homeViewModel;
    private FragmentAccountBinding binding;
    BookViewModel dashboardViewModel;
    AccountBookAdapterGrid bookAdapterGrid;
    FirebaseAuth mAuth;
    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(HomeViewModel.class);
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        dashboardViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        setProgressCourseAdapter();

        mAuth  = FirebaseAuth.getInstance();
        FirebaseUser fuser = mAuth.getCurrentUser();
        FirebaseFirestore datau = FirebaseFirestore.getInstance();
        datau.collection("users").whereEqualTo("uid",fuser.getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               if (!queryDocumentSnapshots.isEmpty()){
                   for (DocumentSnapshot snapshot:queryDocumentSnapshots){
                       user = snapshot.toObject(User.class);
                   }
               }
            }
        });
        //Intent intent = getActivity().getIntent();
        //user  = intent.getParcelableExtra("user");


        binding.changeProfile.setOnClickListener(view -> {
            NavDirections action = (NavDirections) AccountFragmentDirections.actionNavigationAccountToNavigationEditProfile(user);
            Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment_activity_main)).navigate(action);
        });

        binding.logout.setOnClickListener(view -> {
            Toast.makeText(getActivity() ,"Logout",Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            getActivity().finish();
        });

        //akhir
        setBooksGridAdapter();
        View root = binding.getRoot();
        return root;
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
        binding = null;
    }
    public void setProgressCourseAdapter(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false
        );
        binding.rvCourse.setLayoutManager(layoutManager);
        binding.rvCourse.setItemAnimator(new DefaultItemAnimator());
        binding.rvCourse.addItemDecoration(new VerticalSpaceItemDecoration(20));

        FirestoreRecyclerOptions<Course> options = new FirestoreRecyclerOptions.Builder<Course>()
                .setQuery(homeViewModel.getAllCourses(), Course.class)
                .build();
        progressCourseAdapter = new AccountProgressCourseAdapter(options);
        binding.rvCourse.setAdapter(progressCourseAdapter);

    }
    private void setBooksGridAdapter() {
        androidx.recyclerview.widget.GridLayoutManager gridLayoutManager = new androidx.recyclerview.widget.GridLayoutManager(getContext(),2, GridLayoutManager.VERTICAL,false);
        binding.recyclerView2.setLayoutManager(gridLayoutManager);
        binding.recyclerView2.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView2.hasFixedSize();
        FirestoreRecyclerOptions<Book> options_grid = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(dashboardViewModel.getAllBook(), Book.class).build();

        bookAdapterGrid = new AccountBookAdapterGrid(options_grid);

        binding.recyclerView2.setAdapter(bookAdapterGrid);

    }
}