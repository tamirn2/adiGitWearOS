package com.brock.adi.project;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import static java.lang.Math.abs;

public class CountingActivity extends MyWearableActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
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
    private int motion_counter = 1;


    TextView countText;
    TextView exerciseNameText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting);
        countText = findViewById(R.id.countText);
        exerciseNameText = findViewById(R.id.exerciseName);
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

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
                motion_counter++;
                FlowManager.instance.updateCounter(motion_counter);

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
