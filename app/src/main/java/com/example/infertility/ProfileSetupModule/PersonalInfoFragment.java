package com.example.infertility.ProfileSetupModule;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.infertility.R;
import com.example.infertility.Utility;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PersonalInfoFragment extends Fragment {
    private TextInputEditText nameInput, phoneInput, emailInput, passwordInput, confirmPasswordInput;
    private CardView cardViewBirthDayDate;
    private com.google.android.material.textfield.TextInputLayout nameLayout,phoneLayout,emailLayout,passwordLayout,confirmPasswordLayout;
    private androidx.appcompat.widget.AppCompatTextView txtDay, txtMonth, txtYear;
    private Calendar calendar;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public PersonalInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal_info, container, false);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize views
        nameInput = rootView.findViewById(R.id.nameInput);
        phoneInput = rootView.findViewById(R.id.phoneInput);
        emailInput = rootView.findViewById(R.id.emailInput);
        passwordInput = rootView.findViewById(R.id.passwordInput);
        confirmPasswordInput = rootView.findViewById(R.id.confirmPasswordInput);
        txtDay = rootView.findViewById(R.id.txtDay);
        txtMonth = rootView.findViewById(R.id.txtMonth);
        txtYear = rootView.findViewById(R.id.txtYear);
        cardViewBirthDayDate = rootView.findViewById(R.id.cardViewBirthdayDate);
        nameLayout = rootView.findViewById(R.id.nameLayout);
        phoneLayout = rootView.findViewById(R.id.phoneLayout);
        emailLayout = rootView.findViewById(R.id.emailLayout);
        passwordLayout = rootView.findViewById(R.id.passwordLayout);
        confirmPasswordLayout = rootView.findViewById(R.id.confirmPasswordLayout);
        MaterialButton btnSaveChanges = rootView.findViewById(R.id.btnSaveChanges);

        // Set current date
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        txtDay.setText(String.valueOf(day));
        txtMonth.setText(new SimpleDateFormat("MMM", Locale.getDefault()).format(calendar.getTime()));
        txtYear.setText(String.valueOf(year));

        // Pre-fill phone number from OTP verification
        Bundle args = getArguments();
        if (args != null && args.containsKey("phoneNumber")) {
            phoneInput.setText(args.getString("phoneNumber"));
            phoneInput.setEnabled(false); // Disable editing verified phone number
        }

        // Date picker
        cardViewBirthDayDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        calendar.set(selectedYear, selectedMonth, selectedDay);
                        txtDay.setText(String.valueOf(selectedDay));
                        txtMonth.setText(new SimpleDateFormat("MMM", Locale.getDefault()).format(calendar.getTime()));
                        txtYear.setText(String.valueOf(selectedYear));
                    }, year, month, day);
            datePickerDialog.show();
        });

        // Save button
        btnSaveChanges.setOnClickListener(v -> saveUserData());

        return rootView;
    }

    private void saveUserData() {
        String name = nameInput.getText() != null ? nameInput.getText().toString().trim() : "";
        String phone = phoneInput.getText() != null ? phoneInput.getText().toString().trim() : "";
        String email = emailInput.getText() != null ? emailInput.getText().toString().trim() : "";
        String password = passwordInput.getText() != null ? passwordInput.getText().toString().trim() : "";
        String confirmPassword = confirmPasswordInput.getText() != null ? confirmPasswordInput.getText().toString().trim() : "";
        String birthday = String.format("%s %s %s", txtDay.getText(), txtMonth.getText(), txtYear.getText());

        // Validation
        if (name.isEmpty()) {
            nameLayout.setError("Name is required");
            return;
        }
        else{
            nameLayout.setError(null);
        }
        if (phone.isEmpty() || !phone.matches("\\+\\d{10,12}")) {
            phoneLayout.setError("Valid phone number is required");
            return;
        }
        else{
            phoneLayout.setError(null);
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError("Valid email is required");
            return;
        }
        else{
            emailLayout.setError(null);
        }
        if (password.isEmpty() || password.length() < 6) {
            passwordLayout.setError("Password must be at least 6 characters");
            return;
        }
        else{
            passwordLayout.setError(null);
        }
        if (!password.equals(confirmPassword)) {
            confirmPasswordLayout.setError("Passwords do not match");
            return;
        }
        else{
            confirmPasswordLayout.setError(null);
        }

        // Save to Firebase
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(requireContext(), "User not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("phone", phone);
        userData.put("email", email);
        userData.put("birthday", birthday);

        mDatabase.child("users").child(user.getUid()).setValue(userData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(requireContext(), "Profile saved successfully", Toast.LENGTH_SHORT).show();
                    // Navigate to PhysicalInfoFragment
                    Utility.replaceFragment(getParentFragmentManager(), R.id.container_for_profile_screen, new PhysicalInfoFragment(), true);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireContext(), "Failed to save profile: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}