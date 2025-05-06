package com.example.infertility.PeriodQuestionModule;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.infertility.R;
import com.example.infertility.Utility;

public class PeriodQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period_question);

        Utility.replaceFragment(getSupportFragmentManager(), R.id.container_for_period_questions, new PeriodQuestionFirstFragment(), false);

    }
}