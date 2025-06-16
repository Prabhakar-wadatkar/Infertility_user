package com.example.infertility.ProfileSetupModule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.infertility.MainModule.MainActivity;
import com.example.infertility.R;
import com.example.infertility.Utility;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhysicalInfoFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";

    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private SharedPreferences sharedPreferences;
    private ArrayList<String> selectedOptionsForAboutMe = new ArrayList<>();
    private String selectedBodyShape;
    private String selectedFaceShape;

    public PhysicalInfoFragment() {
        // Required empty public constructor
    }

    public static PhysicalInfoFragment newInstance(String param1, String param2) {
        PhysicalInfoFragment fragment = new PhysicalInfoFragment();
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
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_physical_info, container, false);

        // Initialize views
        ImageView backBtn = rootView.findViewById(R.id.back_btn);
        TextView txtSkip = rootView.findViewById(R.id.txtSkip);
        Button btnSave = rootView.findViewById(R.id.btnSave);
        ChipGroup chipGroupAboutMe = rootView.findViewById(R.id.chipGroupAboutMe);
        AutoCompleteTextView dropdownOccupation = rootView.findViewById(R.id.dropdownOccupation);
        AutoCompleteTextView dropdownGender = rootView.findViewById(R.id.dropdown_gender);
        AutoCompleteTextView dropdownEducation = rootView.findViewById(R.id.dropdown_education);
        AutoCompleteTextView dropdownBloodGroup = rootView.findViewById(R.id.dropdown_blood_group);
        AutoCompleteTextView dropdownHeight = rootView.findViewById(R.id.dropdown_height);
        AutoCompleteTextView dropdownWeight = rootView.findViewById(R.id.dropdown_weight);
        AutoCompleteTextView dropdownDrinking = rootView.findViewById(R.id.dropdown_drinking);
        AutoCompleteTextView dropdownSmoking = rootView.findViewById(R.id.dropdown_smoking);
        AutoCompleteTextView dropdownFoodHabit = rootView.findViewById(R.id.dropdown_food_habit);
        AutoCompleteTextView dropdownRelationship = rootView.findViewById(R.id.dropdown_relationship);

        // Back button
        backBtn.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        // Skip button
        txtSkip.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), MainActivity.class));
            getActivity().finish();
        });

        // Set up body shape
        Utility.setupImageLayoutSelection(rootView.findViewById(R.id.container_for_body_shape), shape -> {
            selectedBodyShape = shape;
            Toast.makeText(getContext(), "Selected body shape: " + shape, Toast.LENGTH_SHORT).show();
        });

        // Set up face shape
        Utility.setupImageLayoutSelection(rootView.findViewById(R.id.container_for_face_shape), shape -> {
            selectedFaceShape = shape;
            Toast.makeText(getContext(), "Selected face shape: " + shape, Toast.LENGTH_SHORT).show();
        });

        // Set up about me
        String[] personalityTraits = {"Compassionate", "Dependable", "Angry", "Independent", "Adaptable", "Jealous",
                "Introvert", "Extrovert", "Energetic", "Laid back", "Emotional", "Critical", "Confident",
                "Disciplined", "Indifferent", "Judgmental", "Frustrated", "Irritated"};
        for (String trait : personalityTraits) {
            Utility.addChips(getContext(), trait, chipGroupAboutMe);
        }
        selectedOptionsForAboutMe = Utility.selectedOptions(chipGroupAboutMe);

        // Set up dropdowns
        Utility.setUpDropdown(getContext(), dropdownOccupation, new String[]{"Student", "Teacher", "Engineer", "Doctor"}, item -> {});
        Utility.setUpDropdown(getContext(), dropdownGender, new String[]{"Female", "Male", "Other"}, item -> {});
        Utility.setUpDropdown(getContext(), dropdownEducation, new String[]{"High School", "Undergraduate", "Postgraduate"}, item -> {});
        Utility.setUpDropdown(getContext(), dropdownBloodGroup, new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"}, item -> {});
        Utility.setUpDropdown(getContext(), dropdownHeight, new String[]{"150 cm", "160 cm", "170 cm", "180 cm", "190 cm"}, item -> {});
        Utility.setUpDropdown(getContext(), dropdownWeight, new String[]{"50 kg", "60 kg", "70 kg", "80 kg", "90 kg"}, item -> {});
        Utility.setUpDropdown(getContext(), dropdownDrinking, new String[]{"Never", "Occasionally", "Regularly"}, item -> {});
        Utility.setUpDropdown(getContext(), dropdownSmoking, new String[]{"Never", "Occasionally", "Regularly"}, item -> {});
        Utility.setUpDropdown(getContext(), dropdownFoodHabit, new String[]{"Vegetarian", "Non-Vegetarian", "Vegan"}, item -> {});
        Utility.setUpDropdown(getContext(), dropdownRelationship, new String[]{"Single", "Married", "Divorced", "Widowed"}, item -> {});

        // Save button
        btnSave.setOnClickListener(v -> savePhysicalInfo(
                dropdownOccupation.getText().toString().trim(),
                dropdownGender.getText().toString().trim(),
                dropdownEducation.getText().toString().trim(),
                dropdownBloodGroup.getText().toString().trim(),
                dropdownHeight.getText().toString().trim(),
                dropdownWeight.getText().toString().trim(),
                dropdownDrinking.getText().toString().trim(),
                dropdownSmoking.getText().toString().trim(),
                dropdownFoodHabit.getText().toString().trim(),
                dropdownRelationship.getText().toString().trim()
        ));

        return rootView;
    }

    private void savePhysicalInfo(String occupation, String gender, String education, String bloodGroup,
                                  String height, String weight, String drinking, String smoking,
                                  String foodHabit, String relationship) {
        // Validation
        if (selectedBodyShape == null || selectedBodyShape.isEmpty()) {
            Toast.makeText(getContext(), "Please select a body shape", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedFaceShape == null || selectedFaceShape.isEmpty()) {
            Toast.makeText(getContext(), "Please select a face shape", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedOptionsForAboutMe.isEmpty()) {
            Toast.makeText(getContext(), "Please select at least one personality trait", Toast.LENGTH_SHORT).show();
            return;
        }
        if (occupation.isEmpty()) {
            Toast.makeText(getContext(), "Please select an occupation", Toast.LENGTH_SHORT).show();
            return;
        }
        if (gender.isEmpty()) {
            Toast.makeText(getContext(), "Please select a gender", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save to Firebase
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(getContext(), "User not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> physicalInfo = new HashMap<>();
        physicalInfo.put("body_shape", selectedBodyShape);
        physicalInfo.put("face_shape", selectedFaceShape);
        physicalInfo.put("personality_traits", selectedOptionsForAboutMe);
        physicalInfo.put("occupation", occupation);
        physicalInfo.put("gender", gender);
        physicalInfo.put("education", education.isEmpty() ? "Not specified" : education);
        physicalInfo.put("blood_group", bloodGroup.isEmpty() ? "Not specified" : bloodGroup);
        physicalInfo.put("height", height.isEmpty() ? "Not specified" : height);
        physicalInfo.put("weight", weight.isEmpty() ? "Not specified" : weight);
        physicalInfo.put("drinking", drinking.isEmpty() ? "Not specified" : drinking);
        physicalInfo.put("smoking", smoking.isEmpty() ? "Not specified" : smoking);
        physicalInfo.put("food_habit", foodHabit.isEmpty() ? "Not specified" : foodHabit);
        physicalInfo.put("relationship", relationship.isEmpty() ? "Not specified" : relationship);

        mDatabase.child("users").child(user.getUid()).child("physical_info").setValue(physicalInfo)
                .addOnSuccessListener(aVoid -> {
                    // Save user ID and login status to SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(KEY_IS_LOGGED_IN, true);
                    editor.putString(KEY_USER_ID, user.getUid());
                    editor.apply();

                    Toast.makeText(getContext(), "Physical info saved successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to save physical info: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}