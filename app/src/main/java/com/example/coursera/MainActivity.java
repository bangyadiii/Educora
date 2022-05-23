package com.example.coursera;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.MyBusiness.MyApp.R;
import com.MyBusiness.MyApp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_book, R.id.navigation_search, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

//        code recyclerview---------------------------------------------------
//        recyclerView = findViewById(R.id.recycler_view);
//
//        Integer[] langLogo = {R.drawable.book6,R.drawable.book2,R.drawable.book6};
//
//        String[] langName = {"Buku Fiksi", "Buku Non Fiksi", "Majalah"};
//
//        mainModels = new ArrayList<>();
//        for (int i=0; i<langLogo.length; i++){
//            MainModel model = new MainModel(langLogo[i], langName[i]);
//            mainModels.add(model);
//        }
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(
//                MainActivity.this,LinearLayoutManager.HORIZONTAL,false
//        );
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        mainAdapter = new MainAdapter(MainActivity.this, mainModels);
//
//        recyclerView.setAdapter(mainAdapter);
    }

}