package com.sound.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class TrackingActivity extends AppCompatActivity {

    private Context mCon;
    private Realm realm;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    List<LocationModal> dataModals;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        mCon = this;
        realm = Realm.getDefaultInstance();
        dataModals = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mCon));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        dataModals = realm.where(LocationModal.class).findAll();
        recyclerViewAdapter = new RecyclerViewAdapter(mCon, dataModals);
        recyclerView.setAdapter(recyclerViewAdapter);

        // Define a Handler and a Runnable for updating the data
        Handler handler = new Handler();
        Runnable updateDataRunnable = new Runnable() {
            @Override
            public void run() {
                // Fetch new data from Realm
                RealmResults<LocationModal> newData = realm.where(LocationModal.class).findAll();

                // Update the adapter with the new data
                recyclerViewAdapter.setData(newData);
                recyclerViewAdapter.notifyDataSetChanged();

                // Scroll to the bottom of the RecyclerView
                int lastItemPosition = recyclerViewAdapter.getItemCount() - 1;
                recyclerView.scrollToPosition(lastItemPosition);

                // Schedule the next update after 5 seconds
                handler.postDelayed(this, 5000);
            }
        };

// Start the initial update
        handler.post(updateDataRunnable);
    }

}