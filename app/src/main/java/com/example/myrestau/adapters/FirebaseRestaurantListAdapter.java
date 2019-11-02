package com.example.myrestau.adapters;

import android.content.Context;
import android.content.Intent;

import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.myrestau.Constants;
import com.example.myrestau.R;
import com.example.myrestau.models.Restaurant;
import com.example.myrestau.ui.RestaurantDetailActivity;
import com.example.myrestau.ui.RestaurantDetailFragment;
import com.example.myrestau.util.ItemTouchHelperAdapter;
import com.example.myrestau.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;




public class FirebaseRestaurantListAdapter extends FirebaseRecyclerAdapter<Restaurant, FirebaseRestaurantViewHolder> implements ItemTouchHelperAdapter {
    private Query mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Restaurant> mRestaurants = new ArrayList<>();
    private int mOrientation;


    public FirebaseRestaurantListAdapter(FirebaseRecyclerOptions<Restaurant> options,
                                         Query ref,
                                         OnStartDragListener onStartDragListener,
                                         Context context){
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mRestaurants.add(dataSnapshot.getValue(Restaurant.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onBindViewHolder(@NonNull final FirebaseRestaurantViewHolder viewHolder,int position, Restaurant model ) {
        viewHolder.bindRestaurant(model);
        mOrientation = viewHolder.itemView.getResources().getConfiguration().orientation;
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            createDetailFragment(0);
        }
        viewHolder.mRestaurantImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = viewHolder.getAdapterPosition();
                    if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                        createDetailFragment(itemPosition);
                    } else {
                        Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
                        intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                        intent.putExtra(Constants.EXTRA_KEY_RESTAURANTS, Parcels.wrap(mRestaurants));
                        mContext.startActivity(intent);
                    }
                }
            });
        }

    @NonNull
    @Override
    public FirebaseRestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item_drag, parent, false);
        return new FirebaseRestaurantViewHolder(view);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition){
        Collections.swap(mRestaurants, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        setIndexInForebase();
        return false;
    }

    @Override
    public void onItemDismiss(int position){
        mRestaurants.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInForebase(){
        for(Restaurant restaurant: mRestaurants){
            int index = mRestaurants.indexOf(restaurant);
            DatabaseReference mReference = getRef(index);
            restaurant.setIndex(Integer.toString(index));
            mReference.setValue(restaurant);
        }
    }

    @Override
    public void stopListening(){
        super.stopListening();
        mRef.removeEventListener(mChildEventListener);
    }


    private void createDetailFragment(int position) {
        // Creates new RestaurantDetailFragment with the given position:
        RestaurantDetailFragment detailFragment = RestaurantDetailFragment.newInstance(mRestaurants, position);
        // Gathers necessary components to replace the FrameLayout in the layout with the RestaurantDetailFragment:
        FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        //  Replaces the FrameLayout with the RestaurantDetailFragment:
        ft.replace(R.id.restaurantDetailContainer, detailFragment);
        // Commits these changes:
        ft.commit();
    }

}
