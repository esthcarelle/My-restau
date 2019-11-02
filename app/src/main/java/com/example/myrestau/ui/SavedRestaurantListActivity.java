package com.example.myrestau.ui;


import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestau.Constants;
import com.example.myrestau.R;

import butterknife.ButterKnife;


public class SavedRestaurantListActivity extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_restaurant_list);
        ButterKnife.bind(this);


    }



//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if(mFirebaseAdapter!= null) {
//            mFirebaseAdapter.stopListening();
//        }
//    }
}
