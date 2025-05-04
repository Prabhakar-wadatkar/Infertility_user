package com.example.infertility.ItemModels;

import java.util.List;

public class Question {
    private String questionText;
    private List<String> options;

    public Question(String questionText, List<String> options) {
        this.questionText = questionText;
        this.options = options;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }
}
