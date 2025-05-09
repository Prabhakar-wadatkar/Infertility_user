package com.example.infertility.LoginModule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.infertility.R;
import com.example.infertility.Utility;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginOrSignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginOrSignupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginOrSignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginOrSignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginOrSignupFragment newInstance(String param1, String param2) {
        LoginOrSignupFragment fragment = new LoginOrSignupFragment();
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
       View rootView = inflater.inflate(R.layout.fragment_login__signup, container, false);

        Button btnLoginToContinue = rootView.findViewById(R.id.btnLoginToContinue);
        btnLoginToContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.replaceFragment(getParentFragmentManager(), R.id.container_for_login_screen, new SignInWithEmailAndPasswordFragment(), true);
            }
        });

        Button btnSignupToContinue = rootView.findViewById(R.id.btnSignupToContinue);
        btnSignupToContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.replaceFragment(getParentFragmentManager(), R.id.container_for_login_screen, new SignInWithPhoneNumber(), true);
            }
        });
        return rootView;
    }
}