package com.brock.adi.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;

public class WorkoutDoneActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idle);

        UIManager uiManager = UIManager.getInstance();

        Intent intent = getIntent();
        String exerciseName = intent.getStringExtra("curExerciseName");
        int exerciseCount = intent.getIntExtra("curExerciseCount", 0);
        uiManager.addExerciseToWorkout(exerciseName, exerciseCount);

        Intent idleIntent = new Intent(WorkoutDoneActivity.this, IdleActivity.class);
        WorkoutDoneActivity.this.startActivity(idleIntent);

    }
}
