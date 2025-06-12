package com.example.infertility.LoginModule;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.infertility.ProfileSetupModule.ProfileSetupActivity;
import com.example.infertility.R;
import com.example.infertility.Utility;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignInWithPhoneNumber extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    private String verificationId;
    private ProgressBar loadingSpinner;

    public SignInWithPhoneNumber() {
    }

    public static SignInWithPhoneNumber newInstance(String param1, String param2) {
        SignInWithPhoneNumber fragment = new SignInWithPhoneNumber();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAuth = FirebaseAuth.getInstance();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_in_with_phone_number, container, false);

        // Back button
        CardView cardView = rootView.findViewById(R.id.cardViewBackButton);
        cardView.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        // Phone number input
        TextInputEditText phoneInput = rootView.findViewById(R.id.phoneInput);
        MaterialButton btnNext = rootView.findViewById(R.id.btnNext);
        loadingSpinner = rootView.findViewById(R.id.loadingSpinner);

        btnNext.setOnClickListener(v -> {
            String phoneNumber = phoneInput.getText() != null ? phoneInput.getText().toString().trim() : "";
            if (phoneNumber.isEmpty() || phoneNumber.length() < 10 || !phoneNumber.matches("\\d+")) {
                phoneInput.setError("Enter a valid phone number (digits only)");
                return;
            }
            if (!phoneNumber.startsWith("+")) {
                phoneNumber = "+91" + phoneNumber;
            }
            btnNext.setEnabled(false); // Disable button during request
            loadingSpinner.setVisibility(View.VISIBLE); // Show spinner
            sendVerificationCode(phoneNumber);
        });

        return rootView;
    }

    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(requireActivity())
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                        loadingSpinner.setVisibility(View.GONE); // Hide spinner
                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        loadingSpinner.setVisibility(View.GONE); // Hide spinner
                        String errorMessage;
                        if (e instanceof FirebaseTooManyRequestsException) {
                            errorMessage = "Too many requests. Please try again later or use a different device/phone number.";
                        } else {
                            errorMessage = "Verification failed: " + e.getMessage();
                        }
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
                        requireView().findViewById(R.id.btnNext).setEnabled(true); // Re-enable button
                    }

                    @Override
                    public void onCodeSent(@NonNull String verId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        loadingSpinner.setVisibility(View.GONE); // Hide spinner
                        verificationId = verId;
                        Bundle bundle = new Bundle();
                        bundle.putString("verificationId", verificationId);
                        bundle.putString("phoneNumber", phoneNumber);
                        Number_Otp_Fragment otpFragment = new Number_Otp_Fragment();
                        otpFragment.setArguments(bundle);
                        Utility.replaceFragment(getParentFragmentManager(), R.id.container_for_login_screen, otpFragment, true);
                        requireView().findViewById(R.id.btnNext).setEnabled(true); // Re-enable button
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(requireActivity(), ProfileSetupActivity.class));
                        requireActivity().finish();
                    } else {
                        Toast.makeText(requireContext(), "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}