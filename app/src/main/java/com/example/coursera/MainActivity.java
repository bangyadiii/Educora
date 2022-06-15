package com.example.coursera;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.example.coursera.databinding.ActivityMainBinding;
import com.example.coursera.databinding.LayoutBottomSheetBinding;
import com.example.coursera.ui.home.MateriDetailFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private LayoutBottomSheetBinding bottomSheetBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = binding.navView;
        navView.setItemIconTintList(null);
        navView.setBackground(null);



        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_book, R.id.navigation_search, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

//        binding.floatingActionButton.setOnClickListener(view -> {
//            showBottomSheet();
//        });


    }

    private void showBottomSheet(){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this, com.google.android.material.R.style.Theme_Design_BottomSheetDialog);
//        LayoutBottomSheetBinding binding = LayoutBottomSheetBinding.inflate(getLayoutInflater());
        bottomSheetBinding = LayoutBottomSheetBinding.inflate(getLayoutInflater());

        bottomSheetDialog.setContentView(bottomSheetBinding.getRoot());
        bottomSheetDialog.show();

    }


}