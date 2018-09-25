package com.brock.adi.project;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class CountingActivity extends MyWearableActivity {

    TextView countText;
    TextView exerciseNameText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting);
        countText = findViewById(R.id.countText);
        exerciseNameText = findViewById(R.id.exerciseName);

        FlowManager.instance.getExerciseName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                exerciseNameText.setText(s);
            }
        });

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowManager.instance.backToMenu();
            }
        });

        FlowManager.instance.getCurrentState().observe(this, new Observer<State>() {
            @Override
            public void onChanged(@Nullable State state) {
                if (state == State.IDLE)  {
                    Intent idleIntent = new Intent(CountingActivity.this, IdleActivity.class);
                    CountingActivity.this.startActivity(idleIntent);
                    finish();
                }
                else if (state == State.NFC_CONNECTED) {
                    Intent nfcConnectedIntent = new Intent(CountingActivity.this, NFCConnectedActivity.class);
                    CountingActivity.this.startActivity(nfcConnectedIntent);
                    finish();
                }
                else if (state == State.COUNTING){
                    FlowManager.instance.getCounter().observe(CountingActivity.this, new Observer<Integer>() {
                        @Override
                        public void onChanged(@Nullable Integer count) {
                            if (count != null ) countText.setText(String.format(Locale.getDefault(), "%d", count));
                            else{
                                throw new RuntimeException("count is null");
                            }
                        }
                    });
                }
            }
        });

    }
}
