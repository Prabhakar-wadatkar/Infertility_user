package com.example.infertility.ProfileSetupModule;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infertility.MainModule.MainActivity;
import com.example.infertility.R;
import com.example.infertility.Utility;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhysicalInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhysicalInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PhysicalInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhysicalInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
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
    }

    private ArrayList<String> selectedOptionsForAboutMe = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_physical_info, container, false);
        ImageView back_btn = rootView.findViewById(R.id.back_btn);

        Button btnSaveUserData = rootView.findViewById(R.id.btnSave);
        btnSaveUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        TextView txtSkip = rootView.findViewById(R.id.txtSkip);
        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // Set up the back button click listener
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // Set up for body shape
        Utility.setupImageLayoutSelection(rootView.findViewById(R.id.container_for_body_shape), new Utility.ShapeSelectionListener() {
            @Override
            public void onShapeSelected(String shape) {
                // Handle the selected body shape here

            }
        });

        // Set up for face shape
        Utility.setupImageLayoutSelection(rootView.findViewById(R.id.container_for_face_shape), new Utility.ShapeSelectionListener() {
            @Override
            public void onShapeSelected(String shape) {
                // Handle the selected face shape here

            }
        });


        // Set up for about me
        ChipGroup chipGroupAboutMe = rootView.findViewById(R.id.chipGroupAboutMe);
        String[] personalityTraits = {"Compassionate", "Dependable", "Angry", "Independant", "Adaptable", "Jealous", "Introvert", "Extrovert", "Energetic", "Laid back", "Emotional", "Critical", "Confident", "Disciplined", "Indifferent", "Judgemental", "Frustrated", "Irritated"};
        // Inflate traits in chips form
        for (String text : personalityTraits){
           Utility.addChips(getContext(), text, chipGroupAboutMe);
        }

        // Get selected options in this arraylist
        selectedOptionsForAboutMe = Utility.selectedOptions(chipGroupAboutMe);



        // DrownDown for Occupation
        Utility.setUpDropdown(getContext(), rootView.findViewById(R.id.dropdownOccupation), new String[]{"Student", "Teacher"}, new Utility.OnDropdownItemSelectedListener() {
            @Override
            public void onItemSelected(String selectedItem) {
              // Handle selected item here
            }
        });

        // DrownDown for Gender
        Utility.setUpDropdown(getContext(), rootView.findViewById(R.id.dropdown_gender), new String[]{"Female", "Male"}, new Utility.OnDropdownItemSelectedListener() {
            @Override
            public void onItemSelected(String selectedItem) {
                // Handle selected item here
            }
        });



        return rootView;
    }
}