package com.brock.adi.project;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class LauncherActivity extends MyWearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // todo add here everything you need to setup (sensors, services. permissions etc)

        FlowManager.instance.getCurrentState().observe(this, new Observer<State>() {
            @Override
            public void onChanged(@Nullable State state) {
                if (state == null) { return; }
                switch (state) {
                    case IDLE:
                        startActivity(new Intent(LauncherActivity.this, IdleActivity.class));
                        finish();
                        return;
                    case NFC_CONNECTED:
                        startActivity(new Intent(LauncherActivity.this, NFCConnectedActivity.class));
                        finish();
                        return;
                    case COUNTING:
                        startActivity(new Intent(LauncherActivity.this, CountingActivity.class));
                        finish();
                }
            }
        });
    }
}
