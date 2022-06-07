package com.example.coursera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.coursera.databinding.ActivityWelcomeBinding;


public class WelcomeActivity extends AppCompatActivity {

    ActivityWelcomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater() );
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(view -> {
            startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        });

        binding.btnRegister.setOnClickListener(view -> {
            startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));

        });

    }
}