package com.example.infertility.LoginModule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.infertility.MainModule.MainActivity;
import com.example.infertility.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInWithEmailAndPasswordFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";

    private String mParam1;
    private String mParam2;
    private DatabaseReference mDatabase;
    private SharedPreferences sharedPreferences;

    public SignInWithEmailAndPasswordFragment() {
        // Required empty public constructor
    }

    public static SignInWithEmailAndPasswordFragment newInstance(String param1, String param2) {
        SignInWithEmailAndPasswordFragment fragment = new SignInWithEmailAndPasswordFragment();
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
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_in_with_email_and_password, container, false);

        // Initialize views
        CardView cardViewBackButton = rootView.findViewById(R.id.cardViewBackButton);
        TextInputEditText editTextEmail = rootView.findViewById(R.id.editTextEmail);
        TextInputEditText editTextPassword = rootView.findViewById(R.id.editTextPassword);
        MaterialButton btnSubmit = rootView.findViewById(R.id.btnSubmit);
//        View textViewForgotPassword = rootView.findViewById(R.id.textViewForgotPassword);

        // Back button click
        cardViewBackButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        // Forgot password click (stub for future implementation)
//        textViewForgotPassword.setOnClickListener(v -> Toast.makeText(getContext(), "Forgot Password clicked (Not implemented)", Toast.LENGTH_SHORT).show());

        // Submit button click
        btnSubmit.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (validateInputs(email, password)) {
                signInWithEmailAndPassword(email, password);
            }
        });

        return rootView;
    }

    private boolean validateInputs(String email, String password) {
        if (email.isEmpty()) {
            Toast.makeText(getContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(getContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void signInWithEmailAndPassword(String email, String password) {
        mDatabase.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String storedPassword = userSnapshot.child("password").getValue(String.class);
                        String userId = userSnapshot.getKey();

                        if (storedPassword != null && storedPassword.equals(password)) {
                            // Save sign-in status to SharedPreferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(KEY_IS_LOGGED_IN, true);
                            editor.putString(KEY_USER_ID, userId);
                            editor.apply();

                            Toast.makeText(getContext(), "Sign-in successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), MainActivity.class));
                            requireActivity().finish();
                        } else {
                            Toast.makeText(getContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }
                } else {
                    Toast.makeText(getContext(), "Email not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}