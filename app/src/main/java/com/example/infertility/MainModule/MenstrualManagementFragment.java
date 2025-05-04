package com.example.infertility.MainModule;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.infertility.Adapters.ProductAdapter;
import com.example.infertility.ItemModels.ProductItem;
import com.example.infertility.PCOSQuestionModule.PcosQuestionsActivity;
import com.example.infertility.PeriodQuestionModule.PeriodQuestionActivity;
import com.example.infertility.PeriodQuestionModule.PeriodQuestionFirstFragment;
import com.example.infertility.R;
import com.example.infertility.Utility;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenstrualManagementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenstrualManagementFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenstrualManagementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenstrualManagementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenstrualManagementFragment newInstance(String param1, String param2) {
        MenstrualManagementFragment fragment = new MenstrualManagementFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_menstrual_management, container, false);


        ImageView back_btn = rootView.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        RecyclerView productRecyclerView = rootView.findViewById(R.id.productRecyclerView);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        Button btnAssesYourPeriod = rootView.findViewById(R.id.btnAssesYourPeriod);
        btnAssesYourPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), PeriodQuestionActivity.class);
                startActivity(i);
            }
        });


        Button btn_check_for_pcos = rootView.findViewById(R.id.btn_check_for_pcos);
        btn_check_for_pcos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), PcosQuestionsActivity.class);
                startActivity(i);
            }
        });

        ArrayList<ProductItem> productList = new ArrayList<>();
        productList.add(new ProductItem(R.drawable.product, "Product 1", "description_about_the_product", "4.5(99)", "INR 199"));
        productList.add(new ProductItem(R.drawable.product, "Product 2", "description_about_the_product", "4.5(99)", "INR 199"));
        productList.add(new ProductItem(R.drawable.product, "Product 3", "description_about_the_product", "4.5(99)", "INR 199"));
        productList.add(new ProductItem(R.drawable.product, "Product 4", "description_about_the_product", "4.5(99)", "INR 199"));
        productList.add(new ProductItem(R.drawable.product, "Product 5", "description_about_the_product", "4.5(99)", "INR 199"));

        ProductAdapter productAdapter = new ProductAdapter(getContext(), productList);
        productRecyclerView.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();



        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
        return rootView;
    }



}