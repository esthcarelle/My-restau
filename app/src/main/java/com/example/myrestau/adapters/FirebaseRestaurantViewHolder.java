package com.example.myrestau.adapters;


import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.myrestau.R;
import com.example.myrestau.models.Restaurant;
import com.example.myrestau.util.ItemTouchHelperViewHolder;
import com.squareup.picasso.Picasso;



public class FirebaseRestaurantViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    View mView;
    Context mContext;
    public ImageView mRestaurantImageView;

    public FirebaseRestaurantViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindRestaurant(Restaurant restaurant){
        mRestaurantImageView = mView.findViewById(R.id.restaurantImageView);
        TextView nameTextView = mView.findViewById(R.id.restaurantNameTextView);
        TextView categoryTextView = mView.findViewById(R.id.categoryTextView);
        TextView ratingTextView = mView.findViewById(R.id.ratingTextView);

        nameTextView.setText(restaurant.getName());
        categoryTextView.setText(restaurant.getCategories().get(0));
        ratingTextView.setText("Rating: " + restaurant.getRating() + "/5");
        Picasso.get().load(restaurant.getImageUrl()).into(mRestaurantImageView);
    }

    @Override
    public void onItemSelected(){
        //Log.d("Animation", "onItemSelected");
        // we will add animations here
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,R.animator.drag_scale_on);
        set.setTarget(itemView);
        set.start();
    }

    @Override
    public void onItemClear(){
        //Log.d("Animation", "onItemClear");
        // we will add animations here
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.drag_scale_off);
        set.setTarget(itemView);
        set.start();
    }
}
