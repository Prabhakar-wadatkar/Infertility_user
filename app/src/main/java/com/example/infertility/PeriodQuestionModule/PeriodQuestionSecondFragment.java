package com.example.infertility.PeriodQuestionModule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.infertility.R;
import com.example.infertility.Utility;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PeriodQuestionSecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PeriodQuestionSecondFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PeriodQuestionSecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PeriodQuestionSecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PeriodQuestionSecondFragment newInstance(String param1, String param2) {
        PeriodQuestionSecondFragment fragment = new PeriodQuestionSecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    ArrayList<String> selectedOptionsForSecondQuestion = new ArrayList<>();
    ArrayList<String> selectedOptionsForThirdQuestion = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_period_question_second, container, false);

        Button btn_next_question = rootView.findViewById(R.id.btn_next_question);
        btn_next_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.replaceFragment(getParentFragmentManager(), R.id.container_for_period_questions, new PeriodQuestionThirdFragment(), true);
            }
        });

        ChipGroup chipGroupSecondQuestion = rootView.findViewById(R.id.chipGroupSecondQuestion);
        ChipGroup chipGroupThirdQuestion = rootView.findViewById(R.id.chipGroupThirdQuestion);

        String[] symptomsYouFaceCurrently = {"Cramps", "Bloating", "Headache", "Back pain", "Nausea",
                "Mood swings", "Fatigue", "Tender breasts", "Acne or breakouts", "Food cravings",
                "Diarrhea or constipation", "Trouble sleeping", "Dizziness", "Irritability", "Anxiety"
        };

        for (String options: symptomsYouFaceCurrently) {
            Utility.addChipsForQuestions(getContext(), options, chipGroupSecondQuestion);
        }

        String[] usualCurrentDiet = {"Vegetarian", "Non-Vegetarian", "Vegan", "Pescatarian", "Eggetarian", "Keto",
                "High Protein", "Low Carb", "Balanced Diet", "Junk/Fast Food", "Intermittent Fasting",
                "Gluten-Free", "Dairy-Free", "Unhealthy/Irregular Meals"};

        for (String options: usualCurrentDiet) {
            Utility.addChipsForQuestions(getContext(), options, chipGroupThirdQuestion);
        }

        selectedOptionsForSecondQuestion = Utility.selectedOptions(chipGroupSecondQuestion);
        selectedOptionsForThirdQuestion = Utility.selectedOptions(chipGroupThirdQuestion);


        return rootView;
    }
}