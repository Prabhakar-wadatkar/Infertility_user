package com.example.infertility.MainModule;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.infertility.R;
import com.example.infertility.Utility;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Utility.replaceFragment(getSupportFragmentManager(), R.id.container_for_home_screen, new HomeFragment(), false);

    }
}