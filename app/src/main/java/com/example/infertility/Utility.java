package com.example.infertility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utility {

    public static void replaceFragment(FragmentManager fragmentManager, int containerId, Fragment fragment, boolean addToBackStack){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId, fragment).commit();
        if (addToBackStack){
            fragmentTransaction.addToBackStack(null);
        }

    }

   public static void setupImageLayoutSelection(LinearLayout container, ShapeSelectionListener listener) {
        final LinearLayout[] lastSelected = {null};
        int childCount = container.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = container.getChildAt(i);
            if (child instanceof LinearLayout) {
                LinearLayout layout = (LinearLayout) child;
                layout.setOnClickListener(view -> {
                    // Remove background from previously selected layout
                    if (lastSelected[0] != null) {
                        lastSelected[0].setBackgroundResource(R.drawable.bg_unselected);
                    }
                    // Set background to the currently selected layout
                    view.setBackgroundResource(R.drawable.body_shape_selector);
                    // Update last selected reference
                    lastSelected[0] = layout;
                    // Get selected ID as string (e.g., "one", "two")
                    String selectedId = view.getResources().getResourceEntryName(view.getId());
                    // Call the listener method with the selected ID
                    listener.onShapeSelected(selectedId);
                });
            }
        }
    }

    public static void addChips(Context context, String text, ChipGroup chipGroup) {
        Chip chip = (Chip) LayoutInflater.from(context).inflate(R.layout.chip_item, null);
        chip.setId(new Random().nextInt());
        chip.setText(text);
        chipGroup.addView(chip);
    }

    public static void setUpDropdown(Context context, AutoCompleteTextView dropdown ,String[] options, OnDropdownItemSelectedListener listener) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, options);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.onItemSelected(adapterView.getItemAtPosition(i).toString());
            }
        });
    }

    public static ArrayList<String> selectedOptions(ChipGroup chipGroup) {
        ArrayList<String> selectedOptions = new ArrayList<>();
        chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup chipGroup, @NonNull List<Integer> checkedIds) {
                selectedOptions.clear();
                for (int id : checkedIds) {
                    Chip chip = chipGroup.findViewById(id); // ✅ Get chip from chipGroup
                    if (chip != null) { // ✅ Avoid NullPointerException
                        selectedOptions.add(chip.getText().toString());
                    }
                }
            }
        });

        return selectedOptions;
    }

    public interface ShapeSelectionListener {
        void onShapeSelected(String shape);
    }

    public interface OnDropdownItemSelectedListener {
        void onItemSelected(String selectedItem);
    }

}
