package com.example.infertility.ProfileSetupModule;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.infertility.R;
import com.example.infertility.Utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class PersonalInfoFragment extends Fragment {


    public PersonalInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_personal_info, container, false);
        TextView txtDay = rootView.findViewById(R.id.txtDay);
        TextView txtMonth = rootView.findViewById(R.id.txtMonth);
        TextView txtYear = rootView.findViewById(R.id.txtYear);

        // Get current date
        Calendar calendar = Calendar.getInstance();

        // Set initial date in TextViews
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        // Set current date in TextViews
        txtDay.setText(String.valueOf(day));
        txtMonth.setText( new SimpleDateFormat("MMM", Locale.getDefault()).format(calendar.getTime()));
        txtYear.setText(String.valueOf(year));

        // Set user given date in TextViews
        CardView cardViewBirthDayDate = rootView.findViewById(R.id.cardViewBirthdayDate);
        cardViewBirthDayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(year, month, day);

                        // Format and set text
                        txtDay.setText(String.valueOf(day));
                        txtMonth.setText(new SimpleDateFormat("MMM", Locale.getDefault()).format(calendar.getTime()));
                        txtYear.setText(String.valueOf(year));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        Button btnSaveChanges = rootView.findViewById(R.id.btnSaveChanges);
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.replaceFragment(getParentFragmentManager(), R.id.container_for_profile_screen, new PhysicalInfoFragment(), true);
            }
        });
       return rootView;
    }
}