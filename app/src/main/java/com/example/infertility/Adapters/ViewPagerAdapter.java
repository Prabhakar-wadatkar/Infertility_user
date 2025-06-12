package com.example.infertility.Adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.infertility.UserAccountSettingsModule.AccountFragment;
import com.example.infertility.UserAccountSettingsModule.ConsultationFragment;
import com.example.infertility.UserAccountSettingsModule.SubscriptionFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new AccountFragment();
            case 1: return new SubscriptionFragment();
            case 2: return new ConsultationFragment();
            default: return new AccountFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
