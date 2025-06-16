package com.example.infertility.LoginModule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.infertility.MainModule.MainActivity;
import com.example.infertility.R;
import com.example.infertility.Utility;

public class LoginActivity extends AppCompatActivity {
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if user is already logged in
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);

        if (isLoggedIn) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        FrameLayout container_for_login_screen = findViewById(R.id.container_for_login_screen);
        Utility.replaceFragment(getSupportFragmentManager(), R.id.container_for_login_screen, new LoginOrSignupFragment(), false);
    }
}