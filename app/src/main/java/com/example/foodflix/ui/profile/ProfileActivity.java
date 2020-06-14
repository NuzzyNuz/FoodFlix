package com.example.foodflix.ui.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.foodflix.R;
import com.example.foodflix.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import io.reactivex.annotations.NonNull;

public class ProfileActivity extends AppCompatActivity {


    private static final int CHOOSE_IMAGE = 101;
    private static final int REQUEST_IMAGE_CAPTURE = 100;

    private static final String DEFAULT_IMAGE_URL = "https://picsum.photos/200";

    Uri uriProfileImage;

    ImageView imageViewDP, imgViewCam, imgViewPic;
    TextView textEmail, textPhone, textPassword, textVerifyEmail, buttonSave;
    EditText editTextName;
//    Button buttonSave;

    String profileImageUrl;
    String profileImageURI;

    FirebaseAuth mAuth;
    FirebaseUser mAuthUser;

    Toolbar toolbar;

    private ProgressBar progressbar_pic, progressbarContent;
    private View ActivityLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initializeToolbar();


        imageViewDP = findViewById(R.id.image_view_DP);
        imgViewCam = findViewById(R.id.text_view_Cam);
        imgViewPic = findViewById(R.id.text_view_pic);
        textEmail = findViewById(R.id.text_email);
        textVerifyEmail = findViewById(R.id.text_not_verified);
        textPhone = findViewById(R.id.text_phone);
        textPassword = findViewById(R.id.text_password);
        progressbar_pic = findViewById(R.id.progressbar_pic);
        progressbarContent = findViewById(R.id.progressbar);
        buttonSave = findViewById(R.id.button_save);
        editTextName = findViewById(R.id.edit_text_name);
        ActivityLayout = findViewById(R.id.ActivityLayout);
        ActivityLayout.setVisibility(View.VISIBLE);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Account Settings");

        mAuth = FirebaseAuth.getInstance();
        mAuthUser = mAuth.getCurrentUser();

        loadUserInformation();

        imgViewCam.setOnClickListener(view1 -> takePictureIntent());

        imgViewPic.setOnClickListener(view2 -> showImageChooser());

        buttonSave.setOnClickListener(view3 -> saveUserInformation());

        textVerifyEmail.setOnClickListener(view4 -> verifyEmail());

        textPhone.setOnClickListener(view5 -> {
            ActivityLayout.setVisibility(View.GONE);
            toolbar.setTitle("Verify Phone Number");
            getSupportFragmentManager().beginTransaction().add(R.id.frameLayout1, new VerifyPhoneFragment()).commit();

        });

        textEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityLayout.setVisibility(View.GONE);
                toolbar.setTitle("Update Email");
                getSupportFragmentManager().beginTransaction().add(R.id.frameLayout1, new UpdateEmailFragment()).commit();
            }
        });

        textPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityLayout.setVisibility(View.GONE);
                toolbar.setTitle("Change Password");
                getSupportFragmentManager().beginTransaction().add(R.id.frameLayout1, new UpdatePasswordFragment()).commit();
            }
        });

    }

    private void verifyEmail() {
        if (mAuthUser != null) {
            textVerifyEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAuthUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NotNull @NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ProfileActivity.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(ProfileActivity.this, (task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            });
        }
    }


    private void initializeToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }


    private void loadUserInformation() {
        if (mAuthUser != null) {
            if (mAuthUser.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(mAuthUser.getPhotoUrl().toString())
                        .into(imageViewDP);
            }

            textEmail.setText(mAuthUser.getEmail());

            if (mAuthUser.getDisplayName() != null) {
                editTextName.setText(mAuthUser.getDisplayName());
            }

            if (Objects.requireNonNull(mAuthUser.getPhoneNumber()).isEmpty()) {
                textPhone.setText("Add Number");
            } else {
                textPhone.setText(mAuthUser.getPhoneNumber());
            }

            if (mAuthUser.isEmailVerified()) {
                textVerifyEmail.setVisibility(View.INVISIBLE);
            } else {
                textVerifyEmail.setVisibility(View.VISIBLE);
            }
        }
    }

    private void saveUserInformation() {
        String displayName;
        Uri photo;

        if (mAuthUser.getPhotoUrl() != null) {
            photo = mAuthUser.getPhotoUrl();
        } else if (uriProfileImage != null) {
            photo = Uri.parse(profileImageUrl);
        } else {
            photo = Uri.parse(DEFAULT_IMAGE_URL);
        }

        displayName = editTextName.getText().toString().trim();

        if (displayName.isEmpty()) {
            editTextName.setError("Name is Required");
            editTextName.requestFocus();
            return;
        }

        UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .setPhotoUri(photo)
                .build();


        progressbarContent.setVisibility(View.VISIBLE);

        mAuthUser.updateProfile(profile)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                        progressbarContent.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProfileActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void takePictureIntent() {
        Intent takePictureIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent1.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent1, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void showImageChooser() {
        Intent intent2 = new Intent();
        intent2.setType("image/*");
        intent2.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent2, "Select Profile Image"), CHOOSE_IMAGE);
    }

    //Check requestCode & resultCode to upload the image to Firebase Database
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            assert data != null;
            Bundle extras = data.getExtras();
            assert extras != null;
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageViewDP.setImageBitmap(imageBitmap);
            assert imageBitmap != null;
            uploadImageAndSaveUri(imageBitmap);


        } else if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriProfileImage = data.getData();
            try {
                if (android.os.Build.VERSION.SDK_INT >= 29) {
                    // To handle deprication use
                    Bitmap imageBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(ProfileActivity.this.getContentResolver(), uriProfileImage));

                    imageViewDP.setImageBitmap(imageBitmap);
                    uploadImageAndSaveUri(imageBitmap);
                } else {
                    // Use older version
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(ProfileActivity.this.getContentResolver(), uriProfileImage);

                    imageViewDP.setImageBitmap(imageBitmap);
                    uploadImageAndSaveUri(imageBitmap);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void uploadImageAndSaveUri(Bitmap imageBitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("pics/" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] image = baos.toByteArray();

        UploadTask upload = storageRef.putBytes(image);

        if (uriProfileImage != null) {
            progressbar_pic.setVisibility(View.VISIBLE);
            upload.addOnCompleteListener(uploadTask -> {
                if (uploadTask.isSuccessful()) {
                    progressbar_pic.setVisibility(View.INVISIBLE);
                    storageRef.getDownloadUrl().addOnCompleteListener(urlTask -> {
                        profileImageUrl = Objects.requireNonNull(urlTask.getResult()).toString();
                        profileImageURI = this.uriProfileImage.toString();
                        //Toast.makeText(getActivity(),profileImageUrl,Toast.LENGTH_SHORT).show();
                        Toast toast = Toast.makeText(ProfileActivity.this, "Profile Picture Updated!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        imageViewDP.setImageBitmap(imageBitmap);

                    });
                } else {
                    progressbar_pic.setVisibility(View.INVISIBLE);
                    Toast.makeText(this, Objects.requireNonNull(uploadTask.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}