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
 * Use the {@link PeriodQuestionThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PeriodQuestionThirdFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PeriodQuestionThirdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PeriodQuestionThirdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PeriodQuestionThirdFragment newInstance(String param1, String param2) {
        PeriodQuestionThirdFragment fragment = new PeriodQuestionThirdFragment();
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

    ArrayList<String> selectedOptionsForFourthQuestion = new ArrayList<>();
    ArrayList<String> selectedOptionsForFifthQuestion = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_period_question_third, container, false);

        Button btn_next_question = rootView.findViewById(R.id.btn_next_question);
        btn_next_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.replaceFragment(getParentFragmentManager(), R.id.container_for_period_questions, new PeriodQuestionFourthFragment(), true);
            }
        });

        ChipGroup chipGroupFourthQuestion = rootView.findViewById(R.id.chipGroupFourthQuestion);
        ChipGroup chipGroupFifthQuestion = rootView.findViewById(R.id.chipGroupFifthQuestion);

        String[] dailyActivityOptions = {"No Physical Activity", "Walking", "Jogging", "Running", "Gym Workout",
                "Yoga", "Cycling", "Swimming", "Dance", "Home Workout", "Sports (e.g., Badminton, Football)", "Other"};


        for (String options: dailyActivityOptions) {
            Utility.addChipsForQuestions(getContext(), options, chipGroupFourthQuestion);
        }

        String[] feelingOptions = {"Happy", "Sad", "Anxious", "Irritated", "Tired", "Energetic", "In Pain",
                "Moody", "Stressed", "Calm", "Neutral", "Depressed"};


        for (String options: feelingOptions) {
            Utility.addChipsForQuestions(getContext(), options, chipGroupFifthQuestion);
        }

        selectedOptionsForFourthQuestion = Utility.selectedOptions(chipGroupFourthQuestion);
        selectedOptionsForFifthQuestion = Utility.selectedOptions(chipGroupFifthQuestion);


        return rootView;



    }
}