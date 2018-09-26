package com.brock.adi.project;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import static java.lang.Math.abs;

public class NFCConnectedActivity extends MyWearableActivity implements SensorEventListener {

    private float[] samples_x = new float[50];
    private float[] samples_y = new float[50];
    private float[] samples_z = new float[50];
    private int sampleIndex = 0;
    private float convolution_value = 0;
    private float convolution_value_x = 0;
    private float convolution_value_y = 0;
    private float convolution_value_z = 0;
    private float convolution_Detect = 500;
    private int last_detection = 0;

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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sampleIndex == 48)
        {
            this.gestureDetect();
            sampleIndex = 0;
        }

        samples_x[sampleIndex] = sensorEvent.values[0];
        samples_y[sampleIndex] = sensorEvent.values[1];
        samples_z[sampleIndex] = sensorEvent.values[2];

        sampleIndex = sampleIndex + 1;




    }


    public void gestureDetect() {
        convolution_value = 0;
        convolution_value_x = 0;
        convolution_value_y = 0;
        convolution_value_z = 0;



        for (int i = 0; i < 50 ; i++)
        {
            convolution_value_x += samples_x[i];
            convolution_value_y += samples_y[i];
            convolution_value_z += samples_z[i];
        }
        convolution_value = abs(convolution_value_x) + abs(convolution_value_y) + abs(convolution_value_z);


        if(convolution_value < convolution_Detect)
        {
            if(last_detection == 0)
            {
                last_detection = 1;
                FlowManager.instance.updateCounter(1);
            }
        }

        else
        {
            last_detection = 0;
        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
