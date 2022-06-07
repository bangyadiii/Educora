package com.example.coursera;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.coursera.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth  = FirebaseAuth.getInstance();

        binding.createAccount.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        binding.tblLogin.setOnClickListener(v-> {
            String email = binding.emailEdit.getText().toString();
            String password = binding.passEdit.getText().toString();
            loginWithEmailAndPassword(email, password);

        });

    }

    private void loginWithEmailAndPassword(String email, String password) {

        if(!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);

                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }

            }

        });
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(binding.emailEdit.getText().toString())) {
            binding.emailEdit.setError("Required");
            result = false;
        } else {
            binding.emailEdit.setError(null);
        }
        if (TextUtils.isEmpty(binding.passEdit.getText().toString())) {
            binding.passEdit.setError("Required");
            result = false;

        } else {
            binding.passEdit.setError(null);
        }
        return result;
    }

    public void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "Log In First", Toast.LENGTH_SHORT).show();
        }

    }
}