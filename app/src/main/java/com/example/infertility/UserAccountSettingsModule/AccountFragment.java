package com.example.infertility.UserAccountSettingsModule;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.infertility.R;
import com.example.infertility.Utility;
import com.example.infertility.LoginModule.LoginActivity;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.facebook.shimmer.ShimmerFrameLayout;

public class AccountFragment extends Fragment {

    private TextView nameTextView, dobTextView, phoneTextView, emailTextView;
    private TextView bloodGroupTextView, bodyShapeTextView, drinkingTextView;
    private TextView educationTextView, faceShapeTextView, foodHabitTextView;
    private ChipGroup chipGroupPsycologicalDetails, chipGroupFamilyHistory;
    private ShimmerFrameLayout shimmerLayout;
    private View contentLayout;
    private DatabaseReference mDatabase;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_USER_ID = "userId";
    private static final long MIN_SHIMMER_DURATION = 1000; // 2 seconds
    private boolean isDataLoaded = false;
    private boolean isMinTimeElapsed = false;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        // Initialize Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Initialize views
        nameTextView = rootView.findViewById(R.id.textViewName);
        dobTextView = rootView.findViewById(R.id.textViewDob);
        phoneTextView = rootView.findViewById(R.id.textViewPhone);
        emailTextView = rootView.findViewById(R.id.textViewEmail);
        bloodGroupTextView = rootView.findViewById(R.id.textViewBloodGroup);
        bodyShapeTextView = rootView.findViewById(R.id.textViewBodyShape);
        drinkingTextView = rootView.findViewById(R.id.textViewDrinking);
        educationTextView = rootView.findViewById(R.id.textViewEducation);
        faceShapeTextView = rootView.findViewById(R.id.textViewFaceShape);
        foodHabitTextView = rootView.findViewById(R.id.textViewFoodHabit);
        chipGroupPsycologicalDetails = rootView.findViewById(R.id.chipGroupPsycologicalDetails);
        chipGroupFamilyHistory = rootView.findViewById(R.id.chipGroupFamilyHistory);
        TextView logOutTextView = rootView.findViewById(R.id.textView20);
        TextView deleteAccountTextView = rootView.findViewById(R.id.textViewDeleteAccount);
        shimmerLayout = rootView.findViewById(R.id.shimmerLayout);
        contentLayout = rootView.findViewById(R.id.contentLayout);

        // Start shimmer animation
        shimmerLayout.startShimmer();

        // Schedule minimum shimmer duration
        handler.postDelayed(() -> {
            isMinTimeElapsed = true;
            tryStopShimmer();
        }, MIN_SHIMMER_DURATION);

        // Set up default chips for psychological details
        String[] personalityTraits = {"Compassionate", "Dependable", "Angry", "Independent", "Energetic", "Laid Back", "Disciplined", "Critical"};
        for (String trait : personalityTraits) {
            Utility.addChips(getContext(), trait, chipGroupPsycologicalDetails);
        }

        // Set up default chips for family history
        String[] familyHistoryTraits = {"Diabetes", "Obesity", "PCOD/PCOS", "Endometriosis", "Thyroid"};
        for (String trait : familyHistoryTraits) {
            Utility.addChips(getContext(), trait, chipGroupFamilyHistory);
        }

        // Set up logout functionality
        logOutTextView.setOnClickListener(v -> showLogoutConfirmationDialog());

        // Set up delete account functionality (placeholder)
        deleteAccountTextView.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Delete account clicked", Toast.LENGTH_SHORT).show();
            // TODO: Implement delete account logic
        });

        // Fetch and display user data
        fetchUserData();

        return rootView;
    }

    private void fetchUserData() {
        String userId = sharedPreferences.getString(KEY_USER_ID, null);
        if (userId == null) {
            Toast.makeText(getContext(), "Please log in to view your profile", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
            stopShimmer();
            return;
        }

        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isDataLoaded = true;
                tryStopShimmer();

                if (!snapshot.exists()) {
                    Toast.makeText(getContext(), "Please complete your profile", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Fetch personal details
                String name = snapshot.child("name").getValue(String.class);
                String birthday = snapshot.child("birthday").getValue(String.class);
                String phone = snapshot.child("phone").getValue(String.class);
                String email = snapshot.child("email").getValue(String.class);

                // Fetch physical info
                DataSnapshot physicalInfo = snapshot.child("physical_info");
                String bloodGroup = physicalInfo.child("blood_group").getValue(String.class);
                String bodyShape = physicalInfo.child("body_shape").getValue(String.class);
                String drinking = physicalInfo.child("drinking").getValue(String.class);
                String education = physicalInfo.child("education").getValue(String.class);
                String faceShape = physicalInfo.child("face_shape").getValue(String.class);
                String foodHabit = physicalInfo.child("food_habit").getValue(String.class);

                // Update personal details
                nameTextView.setText(name != null ? "Name: " + name : "Name: Not provided");
                dobTextView.setText(birthday != null ? "DOB: " + birthday : "DOB: Not provided");
                phoneTextView.setText(phone != null ? "Phone: " + phone : "Phone: Not provided");
                emailTextView.setText(email != null ? "Email: " + email : "Email: Not provided");

                // Update physical details
                bloodGroupTextView.setText(bloodGroup != null ? "Blood Group: " + bloodGroup : "Blood Group: Not provided");
                bodyShapeTextView.setText(bodyShape != null ? "Body Shape: " + bodyShape : "Body Shape: Not provided");
                drinkingTextView.setText(drinking != null ? "Drinking: " + drinking : "Drinking: Not provided");
                educationTextView.setText(education != null ? "Education: " + education : "Education: Not provided");
                faceShapeTextView.setText(faceShape != null ? "Face Shape: " + faceShape : "Face Shape: Not provided");
                foodHabitTextView.setText(foodHabit != null ? "Food Habit: " + foodHabit : "Food Habit: Not provided");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                isDataLoaded = true;
                tryStopShimmer();
                Toast.makeText(getContext(), "Unable to load profile. Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void tryStopShimmer() {
        if (isDataLoaded && isMinTimeElapsed) {
            stopShimmer();
        }
    }

    private void stopShimmer() {
        if (shimmerLayout != null) {
            shimmerLayout.stopShimmer();
            shimmerLayout.setVisibility(View.GONE);
        }
        if (contentLayout != null) {
            contentLayout.setVisibility(View.VISIBLE);
        }
        handler.removeCallbacksAndMessages(null);
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Log Out")
                .setMessage("Ready to log out? Take care, and honor your body’s rhythm!")
                .setPositiveButton("Log Out", (dialog, which) -> performLogout())
                .setNegativeButton("Cancel", null)
                .setIcon(R.drawable.ic_logout)
                .show();
    }

    private void performLogout() {
        if (getActivity() != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
            Toast.makeText(getContext(), "Logged out. Continue your fertility journey soon!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
        if (shimmerLayout != null) {
            shimmerLayout.stopShimmer();
        }
    }
}