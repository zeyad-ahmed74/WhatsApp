package com.example.whatsapp.ui.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.whatsapp.ui.CallFragment;
import com.example.whatsapp.ui.Camera;
import com.example.whatsapp.ui.ChatFragment;
import com.example.whatsapp.ui.StatusFragment;

public class WhatsAppViewPagerAdapter extends FragmentStateAdapter {

    private int count;
    public WhatsAppViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle , int count) {
        super(fragmentManager, lifecycle);
        this.count=count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new ChatFragment();
            case 1:
                return new StatusFragment();
            case 2:
                return new CallFragment();
            case 3:
                return new Camera();
        }
        return new ChatFragment();
    }

    @Override
    public int getItemCount() {
        return count;
    }
}
