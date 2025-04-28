package com.example.infertility.LoginModule;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.infertility.ProfileSetupModule.ProfileSetupActivity;
import com.example.infertility.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;

public class Number_Otp_Fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    private String verificationId;
    private String phoneNumber;
    private MaterialButton btnSubmit;

    public Number_Otp_Fragment() {
    }

    public static Number_Otp_Fragment newInstance(String param1, String param2) {
        Number_Otp_Fragment fragment = new Number_Otp_Fragment();
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
            verificationId = getArguments().getString("verificationId");
            phoneNumber = getArguments().getString("phoneNumber");
        }
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_number__otp, container, false);

        // Back button
        CardView cardViewBackButtonOtp = rootView.findViewById(R.id.cardViewBackButtonOtp);
        cardViewBackButtonOtp.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        // OTP input
        OtpTextView otpView = rootView.findViewById(R.id.otpView);
        btnSubmit = rootView.findViewById(R.id.btnSubmitOtp);

        // Update phone number display
        TextView phoneNumberTextView = rootView.findViewById(R.id.phoneNumberTextView);
        if (phoneNumber != null) {
            phoneNumberTextView.setText(getString(R.string.enter_the_code_we_sent_to, phoneNumber));
        }

        // Start OTP countdown
        TextView expiryTextView = rootView.findViewById(R.id.textView4);
        startOtpCountdown(expiryTextView);

        btnSubmit.setOnClickListener(v -> {
            String otp = otpView.getOTP();
            if (otp == null || otp.length() != 6) {
                Toast.makeText(requireContext(), "Please enter a valid 6-digit OTP", Toast.LENGTH_SHORT).show();
                return;
            }
            btnSubmit.setEnabled(false);
            verifyOtp(otp);
        });

        // Resend OTP
        rootView.findViewById(R.id.textView2).setOnClickListener(v -> {
            resendOtp();
            startOtpCountdown(expiryTextView);
        });

        return rootView;
    }

    private void verifyOtp(String otp) {
        if (verificationId == null) {
            Toast.makeText(requireContext(), "Verification ID not found", Toast.LENGTH_SHORT).show();
            btnSubmit.setEnabled(true);
            return;
        }

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), task -> {
                    btnSubmit.setEnabled(true);
                    if (task.isSuccessful()) {
                        try {
                            Intent intent = new Intent(getActivity(), ProfileSetupActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } catch (Exception e) {
                            Toast.makeText(requireContext(), "Failed to start ProfileSetupActivity: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(requireContext(), "OTP verification failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void resendOtp() {
        if (phoneNumber == null) {
            Toast.makeText(requireContext(), "Phone number not found", Toast.LENGTH_SHORT).show();
            return;
        }

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(requireActivity())
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        String errorMessage;
                        if (e instanceof FirebaseTooManyRequestsException) {
                            errorMessage = "Too many requests. Please try again later or use a different device/phone number.";
                        } else {
                            errorMessage = "Resend failed: " + e.getMessage();
                        }
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        verificationId = verId;
                        Toast.makeText(requireContext(), "OTP resent successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        try {
                            Intent intent = new Intent(getActivity(), ProfileSetupActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } catch (Exception e) {
                            Toast.makeText(requireContext(), "Failed to start ProfileSetupActivity: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(requireContext(), "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void startOtpCountdown(TextView textView) {
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                textView.setText("Code expires in " + (millisUntilFinished / 1000) + "s");
            }
            public void onFinish() {
                textView.setText("Code expired");
            }
        }.start();
    }
}