package com.example.coursera.ui.account;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.coursera.R;
import com.example.coursera.databinding.FragmentEditProfileBinding;
import com.example.coursera.model.User;
import com.example.coursera.ui.helper.LoadingDialog;
import com.example.coursera.ui.home.HomeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class EditProfileFragment extends Fragment {
    FragmentEditProfileBinding binding;
    private User user;
    public static int RC_TAKE_PHOTO = 10;
    public static int RC_FROM_GALERY = 20;
    private Activity app;
    private HomeViewModel homeViewModel;
    private FirebaseStorage fbStorage;
    private Uri uri;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;



    public EditProfileFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        app = requireActivity();
        binding = FragmentEditProfileBinding.inflate(inflater);
        homeViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(HomeViewModel.class);
        fbStorage = FirebaseStorage.getInstance();



        if (getArguments() != null && getArguments().getParcelable("user") != null) {
            user = getArguments().getParcelable("user");

        }

        binding.namaEdit.setText(user.getName());
        binding.usernameEdit.setText(user.getUsername());
        binding.telpEdit.setText(user.getNoHP());
        binding.emailEdit.setText(user.getEmail());
        binding.emailEdit.setFocusable(false);
        binding.emailEdit.setEnabled(false);
        binding.emailEdit.setCursorVisible(false);
        binding.emailEdit.setKeyListener(null);
        showAvatar();
        binding.avatar.setOnClickListener(view -> {
//            selectImage();
            showFileChooser();
        });
        binding.btEditAvatar.setOnClickListener(view -> {
//            selectImage();
            showFileChooser();

        });
        if(uri != null){
            Thread thread = new Thread(() -> {
                try {
                    InputStream inputStream = app.getContentResolver().openInputStream(uri);
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
        binding.tblSimpan.setOnClickListener(view -> {
            LoadingDialog loadingDialog = LoadingDialog.getInstance(requireActivity());
            loadingDialog.startLoadingDialog();
            user.setName(binding.namaEdit.getText().toString());
            user.setUsername(binding.usernameEdit.getText().toString());
            user.setNoHP(binding.telpEdit.getText().toString());
            if(uri != null) {
                try {
                    upload(uri);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                    Toast.makeText(app, "error " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            homeViewModel.editUser(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    loadingDialog.dissmisDialog();
                    if(task.isSuccessful()){
                        Toast.makeText(app, "berhasil update profile", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(requireActivity().findViewById(R.id.nav_host_fragment_activity_main)).popBackStack();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(app, "gagal edit profile", Toast.LENGTH_SHORT).show();
                    
                }
            });
            
        });

        return binding.getRoot();
    }
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            Log.d("OPEN", "filechooser");
            startActivityForResult(
                    intent,
                    RC_FROM_GALERY);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(requireActivity(), "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void selectImage() {
        final CharSequence [] items = {"Kamera", "Galeri", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Ambil dari");
        builder.setItems(items, ((dialogInterface, i) -> {
            if(items[i].equals("Kamera")) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (requireActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || app.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requireActivity().requestPermissions(permissions, 100);
                    } else {

                    }
                } else {

                }


            }else if(items[i].equals("Galeri")){
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            uri = data.getData();

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void upload(@NonNull Uri uri) throws URISyntaxException {
        String fileName = getFileName(requireActivity(), uri);
        user.setAvatar("avatars/" + fileName);
        StorageReference fileRef = fbStorage.getReference().child("avatars/" + fileName );

        UploadTask uploadTask = fileRef.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                exception.printStackTrace();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("UPLOAD", "sukses");
            }
        });
    }

    public static String getFileName(Context context, Uri uri) throws URISyntaxException {
        Log.d("PATH", uri.getScheme());
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME);
                return cursor.getString(column_index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private void showAvatar(){
        if(user != null) {
            StorageReference mStorageReference = FirebaseStorage.getInstance().getReference().child(user.getAvatar());
            try {
                final File localFile = File.createTempFile("book", "png");
                mStorageReference.getFile(localFile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                ((ImageView) context.findViewById(R.id.image_view)).setImageBitmap(bitmap);
                                Glide.with(requireActivity())
                                        .load(bitmap)
                                        .centerCrop()
                                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                        .into(binding.avatar);
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





}