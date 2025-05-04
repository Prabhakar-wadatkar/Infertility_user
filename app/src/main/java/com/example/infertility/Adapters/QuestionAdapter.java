package com.example.infertility.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infertility.ItemModels.Question;
import com.example.infertility.R;
import com.example.infertility.Utility;

import java.util.List;
import java.util.Map;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<Question> questions;
    Map<Integer, String> selectedOptions;

    public QuestionAdapter(List<Question> questions, Map<Integer, String> selectedOptions) {
        this.questions = questions;
        this.selectedOptions = selectedOptions;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item_layout, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        holder.bind(questions.get(position), position);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder{

        TextView questionTextView;
        RadioGroup radioGroup;
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);

            questionTextView = itemView.findViewById(R.id.questionTextView);
            radioGroup = itemView.findViewById(R.id.radioGroup);


        }

        public void bind(Question question, int position){
            radioGroup.removeAllViews();  // Clear previous options if any
            questionTextView.setText(question.getQuestionText());

            for (String option : question.getOptions()) {
                Utility.addRadioButton(option, radioGroup);
            }

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int radioButtonId) {
                    RadioButton radioButton = radioGroup.findViewById(radioButtonId);
                    String selectedOption = radioButton.getText().toString();
                    selectedOptions.put(position, selectedOption);
                }
            });

        }
    }

}

