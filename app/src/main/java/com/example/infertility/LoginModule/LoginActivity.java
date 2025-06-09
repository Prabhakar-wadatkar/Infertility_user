package com.example.infertility.LoginModule;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.infertility.R;
import com.example.infertility.Utility;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        FrameLayout container_for_login_screen = findViewById(R.id.container_for_login_screen);
        Utility.replaceFragment(getSupportFragmentManager(), R.id.container_for_login_screen, new LoginOrSignupFragment(), false);
    }

}