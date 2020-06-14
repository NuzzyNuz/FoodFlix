package com.example.foodflix.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodflix.R;
import com.example.foodflix.ui.home.HomeActivity;
import com.example.foodflix.ui.homeuser.HomeuserActivity;
import com.example.foodflix.ui.register.RegisterActivity;
import com.example.foodflix.ui.resetpassword.ResetPasswordActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    FirebaseUser mAuthUser;
    EditText editTextEmail, editTextPassword;
    ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mAuthUser = mAuth.getCurrentUser();

        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        progressBar = findViewById(R.id.progressbar);

        findViewById(R.id.text_view_register).setOnClickListener(this);
        findViewById(R.id.text_view_forget_password).setOnClickListener(this);
        findViewById(R.id.button_sign_in).setOnClickListener(this);

        if (mAuthUser != null) {
            String UID = mAuthUser.getUid();
            if (UID.equals("IJwCUxpVhKQDym0nJNPw9f9T5043")) {
                Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            } else if (!UID.equals("IJwCUxpVhKQDym0nJNPw9f9T5043")) {
                Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, HomeuserActivity.class));
                finish();
            }
        }

    }

    public void onBackPressed() {
        //do nothing
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuthUser != null) {
            String UID = mAuthUser.getUid();
            if (UID.equals("IJwCUxpVhKQDym0nJNPw9f9T5043")) {
                Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            } else if (!UID.equals("IJwCUxpVhKQDym0nJNPw9f9T5043")) {
                Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, HomeuserActivity.class));
                finish();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_register:
                finish();
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.text_view_forget_password:
                finish();
                startActivity(new Intent(this, ResetPasswordActivity.class));
                break;

            case R.id.button_sign_in:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.INVISIBLE);
                if (task.isSuccessful()) {
                    finish();
//                        Intent intent = new Intent(LoginActivity.this, HomeuserActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
                    Intent restartIntent = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(getBaseContext().getPackageName());
                    restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(restartIntent);

                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}