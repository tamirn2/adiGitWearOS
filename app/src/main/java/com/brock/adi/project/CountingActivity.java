package com.brock.adi.project;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

public class CountingActivity extends WearableActivity {

    TextView countText = (TextView)findViewById(R.id.countText);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idle);


        FlowManager.instance.getCurrentState().observe(this, new Observer<State>() {
            @Override
            public void onChanged(@Nullable State state) {
                if (state == State.IDLE)  {
                    Intent idleIntent = new Intent(CountingActivity.this, IdleActivity.class);
                    CountingActivity.this.startActivity(idleIntent);
                    finish();
                }
                else if (state == State.COUNTING){
                    FlowManager.instance.getCounter().observe(this, new Observer<Integer>() {
                        @Override
                        public void onChanged(@Nullable Integer count) {
                            if (count != null ) countText.setText(count);
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
