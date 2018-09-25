package com.brock.adi.project;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class NFCConnectedActivity extends MyWearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcconnected);

        findViewById(R.id.back_to_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowManager.instance.backToMenu();
            }
        });

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
