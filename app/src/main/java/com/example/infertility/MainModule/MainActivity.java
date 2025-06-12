package com.example.infertility.MainModule;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.infertility.UserAccountSettingsModule.AccountSettingsFragment;
import com.example.infertility.R;
import com.example.infertility.Utility;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utility.replaceFragment(getSupportFragmentManager(), R.id.container_for_home_screen, new HomeFragment(), false);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    // Handle Home
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container_for_home_screen, new HomeFragment(), true);
                    return true;
                } else if (id == R.id.accountSettings) {
                    // Handle You
                    Utility.replaceFragment(getSupportFragmentManager(), R.id.container_for_home_screen, new AccountSettingsFragment(), true);
                    return true;
                } else if (id == R.id.cart) {
                    // Handle Cart
                    return true;
                } else if (id == R.id.diet) {
                    // Handle Health Checkup
                    return true;
                } else if (id == R.id.feeling) {
                    // Handle I'm Feeling
                    return true;
                }
                return false;
            }
        });




    }

}