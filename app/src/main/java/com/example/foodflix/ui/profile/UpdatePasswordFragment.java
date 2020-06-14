package com.example.foodflix.ui.profile;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class UpdatePasswordFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mAuthUser = mAuth.getCurrentUser();

    View layoutPassword, layoutUpdatePassword;
    TextView buttonAuthenticate, buttonPasswordUpdate;
    EditText editTextPassword, editTextNewPassword, editTextNewPasswordConfirm;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutPassword = getView().findViewById(R.id.layoutPassword);
        layoutUpdatePassword = getView().findViewById(R.id.layoutUpdatePassword);
        buttonAuthenticate = getView().findViewById(R.id.button_authenticate);
        buttonPasswordUpdate = getView().findViewById(R.id.button_update);
        editTextPassword = getView().findViewById(R.id.edit_text_password);
        editTextNewPassword = getView().findViewById(R.id.edit_text_new_password);
        editTextNewPasswordConfirm = getView().findViewById(R.id.edit_text_new_password_confirm);
        progressBar = getView().findViewById(R.id.progressbar);

        ImageView imageViewDP = getView().findViewById(R.id.image_viewDP);

        if (mAuthUser.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(mAuthUser.getPhotoUrl().toString())
                    .into(imageViewDP);
        }

        layoutPassword.setVisibility(View.VISIBLE);
        layoutUpdatePassword.setVisibility(View.GONE);

        buttonAuthenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = editTextPassword.getText().toString().trim();

                if (password.isEmpty()) {
                    editTextPassword.setError("Password Required");
                    editTextPassword.requestFocus();
                    return;
                }

                if (mAuthUser != null) {
                    AuthCredential credential = EmailAuthProvider.getCredential(mAuthUser.getEmail(), password);
                    progressBar.setVisibility(View.VISIBLE);
                    mAuthUser.reauthenticate(credential)
                            .addOnCompleteListener(task1 -> {
                                progressBar.setVisibility(View.GONE);
                                if (task1.isSuccessful()) {
                                    layoutPassword.setVisibility(View.GONE);
                                    layoutUpdatePassword.setVisibility(View.VISIBLE);
                                } else {
                                    editTextPassword.setError("Invalid Password");
                                    editTextPassword.requestFocus();
                                }
                            });
                }
            }
        });

        buttonPasswordUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = editTextNewPassword.getText().toString().trim();
                String passwordConfirm = editTextNewPassword.getText().toString().trim();

                if (password.isEmpty() || password.length() < 6) {
                    editTextNewPassword.setError("At least 6 character password required");
                    editTextNewPassword.requestFocus();
                    return;
                }

                if (!password.equals(passwordConfirm)) {
                    editTextNewPasswordConfirm.setError("Passowrds do not match");
                    editTextNewPasswordConfirm.requestFocus();
                    return;
                }

                if (mAuthUser != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    mAuthUser.updatePassword(password)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Updated password successfully! Please Login Again", Toast.LENGTH_SHORT).show();
                                        mAuth.signOut();
                                        getActivity().finish();
                                        startActivity(new Intent(getActivity(), LoginActivity.class));
                                    } else {
                                        Toast.makeText(getActivity(), "Unable to update password. Please Try Again Later.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}