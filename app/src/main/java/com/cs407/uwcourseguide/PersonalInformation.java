package com.cs407.uwcourseguide;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalInformation extends AppCompatActivity {
    String cameraPermission[];
    String storagePermission[];
    String newStoragePermission[];
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private static final int NEW_STORAGE_REQUEST = 300;
    private static final int IMAGE_PICKGALLERY_REQUEST = 400;
    private static final int IMAGE_PICKCAMERA_REQUEST = 500;
    private static final int CAMERA_AND_STORAGE_PERMISSION_REQUEST_CODE = 600;
    private static final int LEGACY_STORAGE_PERMISSION_REQUEST_CODE = 700; // Choose an appropriate value
    private ActivityResultLauncher<String> galleryLauncher;
    Uri imageuri;
    CircleImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        Window window = this.getWindow();
        cameraPermission = new String[]{Manifest.permission.CAMERA};
        newStoragePermission = new String[]{Manifest.permission.READ_MEDIA_IMAGES};
        profilePic = findViewById(R.id.profilePhoto);

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        //String fileName = getFileName(uri);
                        //Log.e("Image Loading", "Selected Filename: " + fileName);

                        // Load the image into the ImageView using Picasso
                        Picasso.get().load(uri).into(profilePic, new Callback() {
                            @Override
                            public void onSuccess() {
                                Log.e("Image Loading", "Image loaded successfully");
                                // You can also show a message to the user if needed
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.e("Image Loading", "Error loading image: " + e.getMessage());
                                // Handle the error, show an error message, or perform any necessary action
                            }
                        });
                    } else {
                        Log.e("Image Loading", "Selected URI is null");
                        // Handle the case where the selected URI is null
                    }
                });

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        getSupportActionBar().hide();

        ImageButton collegeYearButton = findViewById(R.id.yearButton);
        collegeYearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYearDialog();
            }
        });

        ImageButton usernameButton = findViewById(R.id.nameButton);
        usernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNameDialog();
            }
        });

        ImageButton profileButton = findViewById(R.id.profilePhotoButton);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePicDialog();
            }
        });
    }

    private Boolean checkNewStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    // requesting for storage permission
    private void requestStoragePermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST);
    }

    private void requestNewStoragePermission() {
        requestPermissions(newStoragePermission, NEW_STORAGE_REQUEST);
    }

    // checking camera permission ,if given then we can click image using our camera
    private Boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        //boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    // requesting for camera permission if not given
    private void requestCameraPermission() {
        requestPermissions(cameraPermission, CAMERA_REQUEST);
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index != -1) {
                        result = cursor.getString(index);
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else if (uri.getScheme().equals("file")) {
            result = new File(uri.getPath()).getName();
        }
        return result;
    }

    // Here we are showing image pic dialog where we will select
    // and image either from camera or gallery
    private void showImagePicDialog() {
        String options[] = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // if access is not given then we will request for permission
                if (which == 0) {  // Camera
                    if (!checkCameraPermission()) {
                        // First, request storage permission
                        Log.e("Permission", "Requesting storage permission");
                        requestCameraPermission();
                    } else {
                        // Storage permission is already granted, proceed to camera
                        Log.e("Permission", "Storage permission is already granted");
                        pickFromCamera();
                    }
                } else if (which == 1) {  // Gallery
                    if (!checkNewStoragePermission()) {
                        // Request storage permission for gallery
                        Log.e("Permission", "Requesting storage permission for gallery");
                        requestNewStoragePermission();
                    } else {
                        // Storage permission is already granted, proceed to gallery
                        Log.e("Permission", "Storage permission is already granted for gallery");
                        pickFromGallery();
                    }
                }
            }
        });

        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Log.e("ERRORS:", "COME HEREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE2");
            if (requestCode == IMAGE_PICKGALLERY_REQUEST) {
                imageuri = data.getData();

                if (imageuri != null) {
                    String fileName = getFileName(imageuri);
                    Log.e("Image Loading", "Selected Filename: " + fileName);

                    // Load the image into the ImageView using Picasso
                    Picasso.get().load(imageuri).into(profilePic, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.e("Image Loading", "Image loaded successfully");
                            // You can also show a message to the user if needed
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e("Image Loading", "Error loading image: " + e.getMessage());
                            // Handle the error, show an error message, or perform any necessary action
                        }
                    });
                } else {
                    Log.e("Image Loading", "Selected URI is null");
                    // Handle the case where the selected URI is null
                }
            }
            if (requestCode == IMAGE_PICKCAMERA_REQUEST) {

                if (imageuri != null) {
                    // Load the image into the ImageView using Picasso
                    Picasso.get().load(imageuri).into(profilePic, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.e("Image Loading", "Image loaded successfully");
                            // You can also show a message to the user if needed
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e("Image Loading", "Error loading image: " + e.getMessage());
                            // Handle the error, show an error message, or perform any necessary action
                        }
                    });
                } else {
                    Log.e("Image Loading", "Selected URI is null");
                    // Handle the case where the selected URI is null
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Both permissions granted, proceed with picking from the camera
                pickFromCamera();
            } else {
                // Permission denied, show a message or handle it accordingly
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == NEW_STORAGE_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Storage permission granted, proceed with picking from the gallery
                pickFromGallery();
            } else {
                // Permission denied, show a message or handle it accordingly
                Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void pickFromCamera() {
        if (checkCameraPermission()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.Media.TITLE, "Temp_pic");
            contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp Description");
            imageuri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            Intent camerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            camerIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);
            startActivityForResult(camerIntent, IMAGE_PICKCAMERA_REQUEST);
        } else {
            requestCameraPermission();
        }
    }

    // We will select an image from gallery
    private void pickFromGallery() {
        // Check if the permissions are granted
        if (checkNewStoragePermission()) {
            // Launch the gallery activity using the ActivityResultLauncher
            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, IMAGE_PICKGALLERY_REQUEST);
        } else {
            // Request storage permission if not granted
            requestStoragePermission();
        }
    }

    private void openYearDialog() {
        CollegeYearDialog dialogOptions = new CollegeYearDialog();
        dialogOptions.show(getSupportFragmentManager(), "example dialog");
    }

    private void openNameDialog() {
        PersonalInfoNameDialog dialogOptions = new PersonalInfoNameDialog();
        dialogOptions.show(getSupportFragmentManager(), "example dialog");
    }
}