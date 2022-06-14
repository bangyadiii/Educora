package com.example.coursera.ui.account.editaccount;

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
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.coursera.BuildConfig;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class EditProfileFragment extends Fragment {
    FragmentEditProfileBinding binding;
    private User user;
    public final static int RC_TAKE_PHOTO = 10;
    public final static int RC_FROM_GALERY = 20;
    private Activity app;
    private HomeViewModel homeViewModel;
    private StorageReference mStorageRef;
    private Uri contentUri;

    String currentPhotoPath;
    private ExecutorService execGetImage;
    private Handler handler;



    public EditProfileFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        app = requireActivity();

        execGetImage = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
        binding = FragmentEditProfileBinding.inflate(inflater);
        homeViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(HomeViewModel.class);
        mStorageRef = FirebaseStorage.getInstance().getReference();


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
            checkPermissionsThenSelectDialog();

        });
        binding.btEditAvatar.setOnClickListener(view -> {
            checkPermissionsThenSelectDialog();

        });

        binding.tblSimpan.setOnClickListener(view -> {
            LoadingDialog loadingDialog = LoadingDialog.getInstance(requireActivity());
            loadingDialog.startLoadingDialog();
            user.setName(binding.namaEdit.getText().toString());
            user.setUsername(binding.usernameEdit.getText().toString());
            user.setNoHP(binding.telpEdit.getText().toString());
            if(contentUri != null) {
                try {
                    uploadToStorage(contentUri);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                    Toast.makeText(app, "error URI = null" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            homeViewModel.editUser(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    loadingDialog.dissmisDialog();
                    if(task.isSuccessful()){
                        Toast.makeText(app, "berhasil update profile", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(requireActivity().findViewById(R.id.nav_host_fragment_activity_main)).popBackStack(R.id.action_navigation_edit_profile_to_navigation_account, false);
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

    private void checkPermissionsThenSelectDialog(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (app.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || app.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requireActivity().requestPermissions(permissions, 100);
            }else
                selectImage();


        }else
            selectImage();
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); //getExternalFilesDir(Environment.DIRECTORY_PICTURES);


        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void selectImage() {
        final CharSequence [] items = {"Kamera", "Galeri", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Ambil dari");
        builder.setItems(items, ((dialogInterface, i) -> {
            if(items[i].equals("Kamera")) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(app.getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        Toast.makeText(app, "error occur while create image file", Toast.LENGTH_SHORT).show();
                        
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(app, "com.example.coursera", photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(cameraIntent, RC_TAKE_PHOTO);
                    }
                }

                startActivityForResult( cameraIntent, RC_FROM_GALERY);


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
       if(resultCode != RESULT_CANCELED){
           switch (requestCode) {
               case RC_TAKE_PHOTO:
                   if (resultCode == RESULT_OK && currentPhotoPath != null) {

                       Glide.with(this).load(new File(currentPhotoPath)).centerCrop().into(binding.avatar);

                       ////scaning masuk ke gallery android (opsional)//////////////////
                       Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                       File f = new File(currentPhotoPath);
                       contentUri = Uri.fromFile(f);
                       mediaScanIntent.setData(contentUri);
                       app.sendBroadcast(mediaScanIntent);
                       //////////////////////////////////


                   }
                   break;

               case RC_FROM_GALERY:
                   if (resultCode == RESULT_OK && data != null) {

                       contentUri = data.getData();
                       String[] filePathColumn = {MediaStore.Images.Media.DATA};
                       if (contentUri != null) {
                           Cursor cursor = app.getContentResolver().query(contentUri,filePathColumn, null, null, null);
                           execGetImage.execute(new Runnable() {
                               @Override
                               public void run() {
                                   if (cursor != null) {
                                       cursor.moveToFirst();

                                       int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                       String picturePath = cursor.getString(columnIndex);
                                       handler.post(() -> {
                                           Glide.with(app).load(new File(picturePath)).centerCrop().into(binding.avatar);
                                       });

                                       cursor.close();
                                   }

                               }
                           });
                           if (cursor != null) {
                               cursor.moveToFirst();

                               int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                               String picturePath = cursor.getString(columnIndex);

                               Glide.with(this).load(new File(picturePath)).centerCrop().into(binding.avatar);
                               cursor.close();
                           }

                       }

                   }
                   break;
           }

       }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            selectImage();

        } else {
            Toast.makeText(app, "denied", Toast.LENGTH_LONG).show();
        }
    }

    public void uploadToStorage(@NonNull Uri file) throws URISyntaxException {
        String avatar_url = "avatars/" + user.getUid() + ".jpg";
        user.setAvatar(avatar_url);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference fotoRef = mStorageRef.child(avatar_url);
        UploadTask uploadTask = fotoRef.putFile(file);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                exception.printStackTrace();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("UPLOAD", "sukses");
                Toast.makeText(app, "berhasil upload avatars", Toast.LENGTH_SHORT).show();
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
        if(user != null && user.getAvatar() != null) {
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