package com.example.infertility.UserAccountSettingsModule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infertility.R;
import com.example.infertility.Utility;
import com.google.android.material.chip.ChipGroup;


public class AccountFragment extends Fragment {



    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        ChipGroup chipGroupPsycologicalDetails = rootView.findViewById(R.id.chipGroupPsycologicalDetails);
        ChipGroup chipGroupFamilyHistory = rootView.findViewById(R.id.chipGroupFamilyHistory);

        // Set up about me
        String[] personalityTraits = {"Compassionate", "Dependable", "Angry", "Independent", "Energetic", "Laid Back"
        ,"Disciplined", "Critical"};
        for (String trait : personalityTraits) {
            Utility.addChips(getContext(), trait, chipGroupPsycologicalDetails);
        }

        // Set up family history
        String[] familyHistoryTraits = {"Diabetes", "Obesity", "PCOD/PCOS", "Endometriosis", "Thyroid"};
        for (String trait : familyHistoryTraits) {
            Utility.addChips(getContext(), trait, chipGroupFamilyHistory);
        }

        return rootView;
    }
}