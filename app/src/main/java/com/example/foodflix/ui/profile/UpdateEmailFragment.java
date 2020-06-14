package com.example.foodflix.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.foodflix.R;
import com.example.foodflix.ui.login.LoginActivity;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class UpdateEmailFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mAuthUser = mAuth.getCurrentUser();

    View layoutPassword, layoutEmail;
    TextView emailUpdate, passwordUpdate;
    EditText passwordTxt, emailTxt;
    ProgressBar progressbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_email, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutPassword = getView().findViewById(R.id.layoutPassword);
        layoutEmail = getView().findViewById(R.id.layoutUpdateEmail);

        emailUpdate = getView().findViewById(R.id.button_update);
        passwordUpdate = getView().findViewById(R.id.button_authenticate);

        emailTxt = getView().findViewById(R.id.edit_text_email);
        passwordTxt = getView().findViewById(R.id.edit_text_password);

        progressbar = getView().findViewById(R.id.progressbar);

        ImageView imageViewDP = getView().findViewById(R.id.image_viewDP);

        if (mAuthUser.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(mAuthUser.getPhotoUrl().toString())
                    .into(imageViewDP);
        }


        layoutPassword.setVisibility(View.VISIBLE);
        layoutEmail.setVisibility(View.INVISIBLE);

//        getActivity().onBackPressed();

        passwordUpdate.setOnClickListener(view2 -> {
            String password = passwordTxt.getText().toString().trim();

            if (password.isEmpty()) {
                passwordTxt.setError("Password Required");
                passwordTxt.requestFocus();
                return;
            }

            if (mAuthUser != null) {
                AuthCredential credential = EmailAuthProvider.getCredential(mAuthUser.getEmail(), password);
                progressbar.setVisibility(View.VISIBLE);
                mAuthUser.reauthenticate(credential)
                        .addOnCompleteListener(task1 -> {
                            progressbar.setVisibility(View.GONE);
                            if (task1.isSuccessful()) {
                                layoutPassword.setVisibility(View.GONE);
                                layoutEmail.setVisibility(View.VISIBLE);
                            } else {
                                passwordTxt.setError("Invalid Password");
                                passwordTxt.requestFocus();
                                return;
                            }
                        });
            }

        });

        emailUpdate.setOnClickListener(view1 -> {
            String email = emailTxt.getText().toString().trim();

            if (email.isEmpty()) {
                emailTxt.setError("Email Required");
                emailTxt.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailTxt.setError("Valid Email Required");
                emailTxt.requestFocus();
                return;
            }

            progressbar.setVisibility(View.VISIBLE);
            if (mAuthUser != null) {
                mAuthUser.updateEmail(email)
                        .addOnCompleteListener(task -> {
                            progressbar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Updated email successfully! Please Login Again", Toast.LENGTH_SHORT).show();
                                mAuth.signOut();
                                getActivity().finish();
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                            } else {
                                Toast.makeText(getActivity(), "Unable to update email. Please Try Again Later.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        });


    }
}