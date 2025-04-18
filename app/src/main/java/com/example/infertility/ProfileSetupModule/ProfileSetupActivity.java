package com.example.infertility.ProfileSetupModule;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.infertility.R;
import com.example.infertility.Utility;

public class ProfileSetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);

        Utility.replaceFragment(getSupportFragmentManager(), R.id.container_for_profile_screen, new PersonalInfoFragment(), false);

    }
}