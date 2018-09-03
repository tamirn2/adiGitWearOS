package com.brock.adi.project;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.activity.WearableActivity;

public class NFCConnectedActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idle);

        //listen to live data of string

        FlowManager.instance.getCurrentState().observe(this, new Observer<State>() {
            @Override
            public void onChanged(@Nullable State state) {
                if (state == State.IDLE) {
                    Intent idleIntent = new Intent(NFCConnectedActivity.this, IdleActivity.class);
                    NFCConnectedActivity.this.startActivity(idleIntent);
                    finish();
                }
                else if(state == State.COUNTING) {
                    Intent countingIntent = new Intent(NFCConnectedActivity.this, CountingActivity.class);
                    NFCConnectedActivity.this.startActivity(countingIntent);
                    finish();
                }
            }
        });

    }
}
