package com.example.infertility.UserAccountSettingsModule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.infertility.Adapters.ViewPagerAdapter;
import com.example.infertility.LoginModule.LoginActivity;
import com.example.infertility.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AccountSettingsFragment extends Fragment {

    private TextView nameTextView, joinedDateTextView;
    private CircleImageView profileImageView;
    private ShimmerFrameLayout shimmerLayout;
    private View contentLayout;
    private ImageView editButton;
    private DatabaseReference mDatabase;
    private SharedPreferences sharedPreferences;
    private RequestManager glideRequestManager;
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_USER_ID = "userId";
    private static final long MIN_SHIMMER_DURATION = 1000; // 1 second
    private boolean isDataLoaded = false;
    private boolean isMinTimeElapsed = false;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public AccountSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Glide with application context
        glideRequestManager = Glide.with(requireContext().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account_settings, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Initialize views
        nameTextView = rootView.findViewById(R.id.textViewName);
        joinedDateTextView = rootView.findViewById(R.id.textViewJoinedDate);
        profileImageView = rootView.findViewById(R.id.profile_image);
        shimmerLayout = rootView.findViewById(R.id.shimmerLayout);
        contentLayout = rootView.findViewById(R.id.contentLayout);
        editButton = rootView.findViewById(R.id.edit_btn);
        TabLayout tabLayout = rootView.findViewById(R.id.tabLayout);
        ViewPager2 viewPager = rootView.findViewById(R.id.viewPager);

        // Start shimmer animation
        shimmerLayout.startShimmer();

        // Schedule minimum shimmer duration
        handler.postDelayed(() -> {
            isMinTimeElapsed = true;
            tryStopShimmer();
        }, MIN_SHIMMER_DURATION);

        // Set up edit button click listener
        editButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Edit profile clicked", Toast.LENGTH_SHORT).show();
            // TODO: Navigate to profile edit screen
        });

        // Set up ViewPager and TabLayout
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Fetch and display user data
        fetchUserData();

        return rootView;
    }

    private void fetchUserData() {
        String userId = sharedPreferences.getString(KEY_USER_ID, null);
        Log.d("AccountSettings", "Retrieved User ID: " + userId);
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
                    Log.e("AccountSettings", "User data not found for ID: " + userId);
                    if (profileImageView != null) {
                        profileImageView.setImageResource(R.drawable.user_image);
                    }
                    return;
                }

                String name = snapshot.child("name").getValue(String.class);
                Long joinedDateTimestamp = snapshot.child("joinedDate").getValue(Long.class);
                String profileImageUrl = snapshot.child("profileImageUrl").getValue(String.class);

                Log.d("AccountSettings", "Name: " + name);
                Log.d("AccountSettings", "Joined Date: " + joinedDateTimestamp);
                Log.d("AccountSettings", "Profile Image URL: " + profileImageUrl);

                nameTextView.setText(name != null ? name : "Name: Not provided");

                if (joinedDateTimestamp != null) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                        String formattedDate = "Joined " + sdf.format(new Date(joinedDateTimestamp));
                        joinedDateTextView.setText(formattedDate);
                    } catch (Exception e) {
                        joinedDateTextView.setText("Joined: Invalid date");
                        Log.e("AccountSettings", "Date formatting error: " + e.getMessage());
                    }
                } else {
                    joinedDateTextView.setText("Joined: Not provided");
                }

                if (profileImageUrl != null && !profileImageUrl.isEmpty() && isAdded() && getActivity() != null && !getActivity().isFinishing()) {
                    Log.d("AccountSettings", "Loading image from: " + profileImageUrl);
                    glideRequestManager
                            .load(profileImageUrl)
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.user_image)
                                    .error(R.drawable.user_image)
                                    .circleCrop())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    Log.e("Glide", "Image load failed: " + (e != null ? e.getMessage() : "Unknown error"));
                                    Toast.makeText(getContext(), "Failed to load profile image", Toast.LENGTH_SHORT).show();
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    Log.d("Glide", "Image loaded successfully");
                                    return false;
                                }
                            })
                            .into(profileImageView);
                } else {
                    if (profileImageView != null) {
                        profileImageView.setImageResource(R.drawable.user_image);
                    }
                    Log.d("AccountSettings", "No profile image URL or invalid state, using default image");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                isDataLoaded = true;
                tryStopShimmer();
                Toast.makeText(getContext(), "Unable to load profile. Please try again.", Toast.LENGTH_LONG).show();
                Log.e("AccountSettings", "Database error: " + error.getMessage());
                if (profileImageView != null) {
                    profileImageView.setImageResource(R.drawable.user_image);
                }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
        if (shimmerLayout != null) {
            shimmerLayout.stopShimmer();
            shimmerLayout = null;
        }
        if (glideRequestManager != null && profileImageView != null) {
            glideRequestManager.clear(profileImageView);
            profileImageView = null;
        }
    }
}