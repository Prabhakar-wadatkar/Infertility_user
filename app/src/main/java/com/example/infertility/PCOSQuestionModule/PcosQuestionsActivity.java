package com.example.infertility.PCOSQuestionModule;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infertility.Adapters.QuestionAdapter;
import com.example.infertility.ItemModels.Question;
import com.example.infertility.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PcosQuestionsActivity extends AppCompatActivity {

    Map<Integer, String> selectedOptions = new HashMap<>();
    // Define some sample questions
    List<Question> questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pcos_questions);


        questions.add(new Question("How regular is your menstrual cycle?", Arrays.asList("Regular (comes at the same time every month)", "Somewhat regular (varies by a few days)", "Irregular (unpredictable or varies significantly)")));
        questions.add(new Question("Do you experience excessive hair growth on face, chest, or abdomen?", Arrays.asList("Yes", "No", "Not sure")));
        questions.add(new Question("Have you noticed significant hair thinning or male pattern baldness?", Arrays.asList("Yes", "No", "Not sure")));
        questions.add(new Question("Do you experience frequent acne?", Arrays.asList("Yes", "No", "Sometimes")));
        questions.add(new Question("Do you have frequent oily skin?", Arrays.asList("Yes, my skin is often oily throughout the day", "Occasionally, mostly in the T-zone", "No, my skin is generally dry or balanced")));
        questions.add(new Question("Do you experience weight gain, particularly around the abdomen?", Arrays.asList("Yes, I’ve gained weight around my abdomen", "Occasionally, but it’s not consistent", "No, my weight is stable or gained elsewhere")));
        questions.add(new Question("Do you experience frequent mood swings?", Arrays.asList("Yes, I experience frequent mood swings.", "Occasionally, but not too often.", "No, I rarely experience mood swings.")));
        questions.add(new Question("Do you experience frequent anxiety?", Arrays.asList("Yes, I experience frequent anxiety.", "Occasionally, but not too often.", "No, I rarely experience anxiety.")));
        questions.add(new Question("Do you experience frequent depression?", Arrays.asList("Yes, I experience frequent depression.", "Occasionally, but not too often.", "No, I rarely experience depression.")));
        questions.add(new Question("Have you ever been diagnosed with insulin resistance/diabetes?", Arrays.asList("Yes, I have been diagnosed with insulin resistance/diabetes.", "No, I haven’t been diagnosed.", "Not sure, I haven't been tested.")));
        questions.add(new Question("Do you have difficulty in conceiving?", Arrays.asList("Yes, I have difficulty conceiving.", "No, I don’t have any difficulty conceiving.", "Not applicable, I’m not trying to conceive.")));
        questions.add(new Question("Do you experience blood/white discharge between periods?", Arrays.asList("Yes, I experience it frequently.", "Occasionally, but not often.", "No, I don’t experience any discharge between periods.")));

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new QuestionAdapter(questions, selectedOptions));

        Button btn_next_question = findViewById(R.id.btn_next_question);
        btn_next_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Radio button answers here in map
                for (Map.Entry<Integer, String> entry : selectedOptions.entrySet()) {
                    int questionIndex = entry.getKey();
                    String answer = entry.getValue();

                    // Process the answers as needed (e.g., send them to a server or store locally)
                    Log.d("Answer", "Question: " + questions.get(questionIndex).getQuestionText() + ": "+ answer);
                }
            }
        });


    }
}