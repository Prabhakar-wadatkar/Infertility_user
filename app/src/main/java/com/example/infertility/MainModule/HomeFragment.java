package com.example.infertility.MainModule;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infertility.Adapters.CardAdapter;
import com.example.infertility.Adapters.DiscussionAdapter;
import com.example.infertility.Adapters.ProductAdapter;
import com.example.infertility.Adapters.PublishAdapter;
import com.example.infertility.ItemModels.CardItem;
import com.example.infertility.ItemModels.DiscussionItem;
import com.example.infertility.ItemModels.ProductItem;
import com.example.infertility.ItemModels.PublishItem;
import com.example.infertility.R;
import com.example.infertility.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootView = inflater.inflate(R.layout.fragment_home, container, false);

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
        cardViewTrackPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.replaceFragment(getParentFragmentManager(), R.id.container_for_home_screen, new MenstrualManagementFragment(), true);
            }
        });

        CardView cardViewTrackActivity = rootView.findViewById(R.id.cardViewTrackActivity);
        cardViewTrackActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.replaceFragment(getParentFragmentManager(), R.id.container_for_home_screen, new StepsTrackerFragment(), true);
            }
        });

        CardView cardViewTrackNutrition = rootView.findViewById(R.id.cardViewTrackNutrition);
        cardViewTrackNutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.replaceFragment(getParentFragmentManager(), R.id.container_for_home_screen, new NutritionTrackerFragment(), true);
            }
        });




        return rootView;
    }
}