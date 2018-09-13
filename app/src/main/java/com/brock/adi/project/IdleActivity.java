package com.brock.adi.project;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class IdleActivity extends MyWearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idle);

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
                else{
                    throw new RuntimeException("shouldn't get this state here " + state.name());
                }
            }
        });
    }
}
