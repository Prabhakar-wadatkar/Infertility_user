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
 * Use the {@link PeriodQuestionFirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PeriodQuestionFirstFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PeriodQuestionFirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PeriodQuestionFirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PeriodQuestionFirstFragment newInstance(String param1, String param2) {
        PeriodQuestionFirstFragment fragment = new PeriodQuestionFirstFragment();
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

    ArrayList<String> selectedOptionsForFirstQuestion = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_period_question_first, container, false);

        ChipGroup chipGroupFirstQuestion = rootView.findViewById(R.id.chipGroupFirstQuestion);

        String[] options = {"Heavy Flow", "Light Flow", "Weight Flow", "Irregular Flow",
                "Heavy Flow", "Light Flow", "Weight Flow", "Irregular Flow",
                "Heavy Flow", "Light Flow", "Weight Flow", "Irregular Flow"};
        for (String option : options) {
            Utility.addChipsForQuestions(getContext(), option, chipGroupFirstQuestion);
        }

        Button btn_next_question = rootView.findViewById(R.id.btn_next_question);
        btn_next_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.replaceFragment(getParentFragmentManager(), R.id.container_for_period_questions, new PeriodQuestionSecondFragment(), true);
            }
        });

        selectedOptionsForFirstQuestion = Utility.selectedOptions(chipGroupFirstQuestion);


        return rootView;
    }
}