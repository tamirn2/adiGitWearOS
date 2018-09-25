package com.brock.adi.project;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;


public class IdleActivity extends MyWearableActivity {

    WearableRecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idle);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new ExercisesAdapter());
        recyclerView.setLayoutManager(new WearableLinearLayoutManager(this));
        recyclerView.setCircularScrollingGestureEnabled(true);
        
        // Enables Always-on
        setAmbientEnabled();


        FlowManager.instance.getCurrentState().observe(this, new Observer<State>() {
            @Override
            public void onChanged(@Nullable State state) {
                if (state == State.NFC_CONNECTED) {
                    Intent nfcConnectedIntent = new Intent(IdleActivity.this, NFCConnectedActivity.class);
                    IdleActivity.this.startActivity(nfcConnectedIntent);
                    finish();
                }
            }
        });
    }
}
