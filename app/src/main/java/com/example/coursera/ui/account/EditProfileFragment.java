package com.example.coursera.ui.account;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coursera.R;
import com.example.coursera.databinding.FragmentEditProfileBinding;
import com.example.coursera.model.User;

import java.io.IOException;
import java.io.InputStream;


public class EditProfileFragment extends Fragment {
    FragmentEditProfileBinding binding;
    private User user;
    public static int RC_TAKE_PHOTO = 10;
    public static int RC_FROM_GALERY = 20;
    Activity app;


    public EditProfileFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getParcelable("user");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        app = requireActivity();
        binding = FragmentEditProfileBinding.inflate(inflater);

        binding.avatar.setOnClickListener(view -> {
            selectImage();

        });
        binding.btEditAvatar.setOnClickListener(view -> {
            selectImage();
        });

        return binding.getRoot();
    }

    private void selectImage() {
        final CharSequence [] items = {"Ambil gambar", "Ambil dari galeri", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Ambil dari");
        builder.setItems(items, ((dialogInterface, i) -> {
            if(items[i].equals("Ambil gambar")){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(app.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || app.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ){
                        String [] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        app.requestPermissions(permissions, 100);
                    }else
                        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), RC_TAKE_PHOTO);
                }else
                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), RC_TAKE_PHOTO);

            }else if(items[i].equals("Ambil dari galeri")){
                Intent intent  = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, RC_FROM_GALERY);

            }
            else if(items[i].equals("Cancel")){
                dialogInterface.dismiss();
            }

        }));
        builder.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), RC_TAKE_PHOTO);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_TAKE_PHOTO && requestCode == RESULT_OK && data != null){
            final Uri path = data.getData();
            Thread thread = new Thread(() -> {
               try {
                   InputStream inputStream = app.getContentResolver().openInputStream(path);
                   Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                   binding.avatar.post(() -> {
                      binding.avatar.setImageBitmap(bitmap);
                   });

               } catch (IOException e ){
                   e.printStackTrace();
               }
            });
            thread.start();
        }
        else if(requestCode == RC_FROM_GALERY && requestCode == RESULT_OK){
            final Bundle bundle = data.getExtras();
            Thread thread = new Thread(() -> {

                Bitmap bitmap = (Bitmap) bundle.get("data");
                binding.avatar.post(() -> {
                    binding.avatar.setImageBitmap(bitmap);

                });


            });
            thread.start();

        }

    }



}