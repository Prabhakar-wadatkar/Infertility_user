package com.example.infertility.ProfileSetupModule;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.infertility.R;
import com.example.infertility.Utility;

public class ProfileSetupActivity extends AppCompatActivity {
    private static final String TAG = "ProfileSetupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_profile_setup);
            Log.d(TAG, "ProfileSetupActivity displayed successfully");
            Toast.makeText(this, "Welcome to Profile Setup", Toast.LENGTH_SHORT).show();

            // Load PersonalInfoFragment with phone number
            if (savedInstanceState == null) {
                PersonalInfoFragment fragment = new PersonalInfoFragment();
                Bundle args = new Bundle();
                args.putString("phoneNumber", getIntent().getStringExtra("phoneNumber"));
                fragment.setArguments(args);
                Utility.replaceFragment(getSupportFragmentManager(), R.id.container_for_profile_screen, fragment, false);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: " + e.getMessage(), e);
            Toast.makeText(this, "Error loading ProfileSetupActivity: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }
}