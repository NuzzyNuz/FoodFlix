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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class VerifyPhoneFragment extends Fragment {

    View layoutPhone, layoutVerification;
    TextView buttonSendCode, buttonVerify;
    ProgressBar progressBar;
    String verificationID = null;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mAuthUser = mAuth.getCurrentUser();

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks phoneAuthCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        EditText codeEditText = getView().findViewById(R.id.edit_text_code);
                        codeEditText.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    verificationID = s;
//                    Toast.makeText(getActivity(), "Code Sent", Toast.LENGTH_SHORT).show();
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verify_phone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        getActivity().onBackPressed();

        layoutPhone = getView().findViewById(R.id.layoutPhone);
        layoutVerification = getView().findViewById(R.id.layoutVerification);
        buttonSendCode = getView().findViewById(R.id.button_send_verification);
        buttonVerify = getView().findViewById(R.id.button_verify);
        EditText phoneNumEditText = getView().findViewById(R.id.edit_text_phone);
        EditText codeEditText = getView().findViewById(R.id.edit_text_code);
        CountryCodePicker ccp = getView().findViewById(R.id.ccp);
        progressBar = getView().findViewById(R.id.progressbar);
        ImageView imageViewDP = getView().findViewById(R.id.image_viewDP);

        layoutPhone.setVisibility(View.VISIBLE);
        layoutVerification.setVisibility(View.GONE);

        progressBar.setVisibility(View.INVISIBLE);

        if (mAuthUser.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(mAuthUser.getPhotoUrl().toString())
                    .into(imageViewDP);
        }

        buttonSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String phoneNumVal = phoneNumEditText.getText().toString().trim();

                if (phoneNumVal.isEmpty() || phoneNumVal.length() != 9) {
                    phoneNumEditText.setError("Enter a valid phone number");
                    phoneNumEditText.requestFocus();
                    return;
                }

                String countrycode = ccp.getSelectedCountryCode();

                String phoneNumber = "+" + countrycode + phoneNumVal;

                sendVerificationCode(phoneNumber);

                progressBar.setVisibility(View.INVISIBLE);

                layoutPhone.setVisibility(View.GONE);
                layoutVerification.setVisibility(View.VISIBLE);

//               Toast.makeText(getActivity(), "Successfully", Toast.LENGTH_SHORT).show();


            }
        });

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = codeEditText.getText().toString().trim();
                if (!code.isEmpty()) {
                    verifyCode(code);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });


    }

    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                requireActivity(),
                phoneAuthCallbacks);
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
        addPhoneNumber(credential);
    }

    private void addPhoneNumber(PhoneAuthCredential credential) {
        Objects.requireNonNull(FirebaseAuth.getInstance()
                .getCurrentUser())
                .updatePhoneNumber(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Phone Added", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                            startActivity(new Intent(getActivity(), ProfileActivity.class));
                        } else {
                            Toast.makeText(getActivity(), "Error! Please Try Again Later.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}