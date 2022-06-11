package com.example.coursera;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import com.example.coursera.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";

    private FirebaseFirestore mDatabase ;
    private FirebaseAuth mAuth;
    private EditText edtEmail, edtTelp, edtNama, edtPass;
    private Button btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDatabase = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        edtNama = findViewById(R.id.nama_edit);
        edtTelp = findViewById(R.id.telp_edit);
        edtEmail = findViewById(R.id.email_edit);
        edtPass = findViewById(R.id.pass_edit);
        btnDaftar = findViewById(R.id.tbl_regis);

        btnDaftar.setOnClickListener(this);

    }

    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();

        String email = edtEmail.getText().toString();
        String password = edtPass.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        //hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(RegisterActivity.this, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //fungsi dipanggil ketika proses Authentikasi berhasil
    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());


        // membuat User admin baru
        String nama = edtNama.getText().toString();
        String noTelp = edtTelp.getText().toString();
        User user1 = writeNewUser(user.getUid(), user.getEmail(),nama,username, noTelp);

        // Go to MainActivity
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user",user1);
        startActivity(intent);
//        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
//        finish();
    }

    /*
        ini fungsi buat bikin username dari email
            contoh email: abcdefg@mail.com
            maka username nya: abcdefg
     */
    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            edtEmail.setError("Required");
            result = false;
        } else {
            edtEmail.setError(null);
        }

        if (TextUtils.isEmpty(edtPass.getText().toString())) {
            edtPass.setError("Required");
            result = false;
        } else {
            edtPass.setError(null);
        }

        return result;
    }

    // menulis ke Database
    private User writeNewUser(String uid, String email, String name, String username, String noHP) {
        User user = new User(uid,email,name,username,noHP);

        mDatabase.collection("users").add(user);
        //mDatabase.child("users").child(userId).setValue(user);
        return user;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tbl_regis) {
            signUp();
        }

    }
}