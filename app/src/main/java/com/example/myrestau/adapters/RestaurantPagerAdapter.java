package com.example.myrestau.adapters;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myrestau.models.Restaurant;
import com.example.myrestau.ui.RestaurantDetailFragment;

import java.util.ArrayList;


public class RestaurantPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Restaurant> mRestaurants;

    public RestaurantPagerAdapter(FragmentManager fm, ArrayList<Restaurant> restaurants){
        super(fm);
        mRestaurants = restaurants;
    }

    @Override
    public Fragment getItem(int position){
        return RestaurantDetailFragment.newInstance(mRestaurants.get(position));
    }

    @Override
    public int getCount(){
        return mRestaurants.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mRestaurants.get(position).getName();
    }
}
