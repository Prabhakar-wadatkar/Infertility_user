package com.example.infertility.MainModule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.infertility.Adapters.CardAdapter;
import com.example.infertility.Adapters.DiscussionAdapter;
import com.example.infertility.Adapters.ProductAdapter;
import com.example.infertility.Adapters.PublishAdapter;
import com.example.infertility.ItemModels.CardItem;
import com.example.infertility.ItemModels.DiscussionItem;
import com.example.infertility.ItemModels.ProductItem;
import com.example.infertility.ItemModels.PublishItem;
import com.example.infertility.LoginModule.LoginActivity;
import com.example.infertility.R;
import com.example.infertility.UserAccountSettingsModule.AccountSettingsFragment;
import com.example.infertility.MainModule.MenstrualManagementFragment;
import com.example.infertility.MainModule.NutritionTrackerFragment;
import com.example.infertility.MainModule.StepsTrackerFragment;
import com.example.infertility.Utility;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import de.hdodenhof.circleimageview.CircleImageView;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_USER_ID = "userId";

    private String mParam1;
    private String mParam2;
    private DatabaseReference mDatabase;
    private SharedPreferences sharedPreferences;
    private CircleImageView profileImage;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        // Initialize Firebase and SharedPreferences
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize profile image view
        profileImage = rootView.findViewById(R.id.profile_image);
        profileImage.setOnClickListener(view ->
                Utility.replaceFragment(getParentFragmentManager(), R.id.container_for_home_screen, new AccountSettingsFragment(), true)
        );

        // Fetch and display profile image
        fetchProfileImage();

        // Set up RecyclerViews
        RecyclerView recyclerView = rootView.findViewById(R.id.cardRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        List<CardItem> cardList = new ArrayList<>();
        cardList.add(new CardItem(R.drawable.banner4, "What is body type?", "Everybody is a different with different body type..."));
        cardList.add(new CardItem(R.drawable.banner3, "What are Doshas?", "Doshas is prakruti of your body..."));
        cardList.add(new CardItem(R.drawable.banner1, "How many body types?", "There are 4 body types mainly on your vibes..."));
        CardAdapter adapter = new CardAdapter(getContext(), cardList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        RecyclerView productRecyclerView = rootView.findViewById(R.id.productRecyclerView);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        ArrayList<ProductItem> productList = new ArrayList<>();
        productList.add(new ProductItem(R.drawable.product, "Product 1", "description_about_the_product", "4.5(99)", "INR 199"));
        productList.add(new ProductItem(R.drawable.product, "Product 2", "description_about_the_product", "4.5(99)", "INR 199"));
        productList.add(new ProductItem(R.drawable.product, "Product 3", "description_about_the_product", "4.5(99)", "INR 199"));
        productList.add(new ProductItem(R.drawable.product, "Product 4", "description_about_the_product", "4.5(99)", "INR 199"));
        productList.add(new ProductItem(R.drawable.product, "Product 5", "description_about_the_product", "4.5(99)", "INR 199"));
        ProductAdapter productAdapter = new ProductAdapter(getContext(), productList);
        productRecyclerView.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();

        RecyclerView discussionRecyclerView = rootView.findViewById(R.id.discussionRecyclerView);
        discussionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        ArrayList<DiscussionItem> discussionList = new ArrayList<>();
        discussionList.add(new DiscussionItem(R.drawable.banner, "Some days my energy is through the roof, others I'm a total couch potato. Wonder why?"));
        discussionList.add(new DiscussionItem(R.drawable.discussion, "These 'doshas' keep popping up, right? Think of them like your body's vibes."));
        discussionList.add(new DiscussionItem(R.drawable.banner1, "Ever notice how your mood shifts with the weather or food? There's more to it than you think."));
        discussionList.add(new DiscussionItem(R.drawable.banner4, "Understanding your body's signals can turn guesswork into self-care. Let's decode them together."));
        DiscussionAdapter discussionAdapter = new DiscussionAdapter(getContext(), discussionList);
        discussionRecyclerView.setAdapter(discussionAdapter);
        discussionAdapter.notifyDataSetChanged();

        RecyclerView publishRecyclerView = rootView.findViewById(R.id.publishRecyclerView);
        publishRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        ArrayList<PublishItem> publishList = new ArrayList<>();
        publishList.add(new PublishItem(R.drawable.publish, "Business Times"));
        publishList.add(new PublishItem(R.drawable.publish, "Hindustan Times"));
        publishList.add(new PublishItem(R.drawable.publish, "ET Now"));
        publishList.add(new PublishItem(R.drawable.publish, "Business Times"));
        publishList.add(new PublishItem(R.drawable.publish, "Hindustan Times"));
        PublishAdapter publishAdapter = new PublishAdapter(getContext(), publishList);
        publishRecyclerView.setAdapter(publishAdapter);
        publishAdapter.notifyDataSetChanged();

        CardView cardViewTrackPeriod = rootView.findViewById(R.id.cardViewTrackPeriod);
        cardViewTrackPeriod.setOnClickListener(view ->
                Utility.replaceFragment(getParentFragmentManager(), R.id.container_for_home_screen, new MenstrualManagementFragment(), true)
        );

        CardView cardViewTrackActivity = rootView.findViewById(R.id.cardViewTrackActivity);
        cardViewTrackActivity.setOnClickListener(view ->
                Utility.replaceFragment(getParentFragmentManager(), R.id.container_for_home_screen, new StepsTrackerFragment(), true)
        );

        CardView cardViewTrackNutrition = rootView.findViewById(R.id.cardViewTrackNutrition);
        cardViewTrackNutrition.setOnClickListener(view ->
                Utility.replaceFragment(getParentFragmentManager(), R.id.container_for_home_screen, new NutritionTrackerFragment(), true)
        );

        return rootView;
    }

    private void fetchProfileImage() {
        String userId = sharedPreferences.getString(KEY_USER_ID, null);
        Log.d("HomeFragment", "Retrieved User ID: " + userId);
        if (userId == null) {
            Toast.makeText(getContext(), "Please log in to view your profile", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
            return;
        }

        mDatabase.child("users").child(userId).child("profileImageUrl").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String profileImageUrl = snapshot.getValue(String.class);
                Log.d("HomeFragment", "Profile Image URL: " + profileImageUrl);

                if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
                    Glide.with(requireContext())
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
                            .into(profileImage);
                } else {
                    profileImage.setImageResource(R.drawable.user_image);
                    Log.d("HomeFragment", "No profile image URL, using default image");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load profile image: " + error.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("HomeFragment", "Database error: " + error.getMessage());
                profileImage.setImageResource(R.drawable.user_image);
            }
        });
    }
}