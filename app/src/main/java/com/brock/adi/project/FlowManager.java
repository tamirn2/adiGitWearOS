package com.brock.adi.project;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.icu.text.SimpleDateFormat;
import android.os.CountDownTimer;

import java.util.Date;

public class FlowManager {

    private static final long COUNTDOWN_TO_IDLE_MILLIS = 120000;
    private static final long COUNTDOWN_INTERVALS = 12000;
    private CountDownTimer timerUntilIdle = null;

    private MutableLiveData<State> currentState = new MutableLiveData<>();
    private MutableLiveData<String> exerciseName = new MutableLiveData<>();
    private MutableLiveData<Integer> exerciseCounter = new MutableLiveData<>();


    private DataStore dataStore = new DataStore();

    public static final FlowManager instance = new FlowManager();

    private FlowManager(){
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        dataStore.updateWorkoutTime(timeStamp);
        currentState.postValue(State.IDLE);
    }

    private void restartTimers(){
        if (timerUntilIdle != null) timerUntilIdle.cancel();
        timerUntilIdle = null;

        timerUntilIdle = new CountDownTimer(COUNTDOWN_TO_IDLE_MILLIS, COUNTDOWN_INTERVALS) {
            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() {
                goToNewState(State.IDLE);
            }
        };
    }
    public void updateNFCConnected(String name){
        restartTimers();
        dataStore.addExerciseToWorkout(name);
        exerciseName.postValue(name);
        exerciseCounter.postValue(0);
        goToNewState(State.NFC_CONNECTED);
    }

    public void updateCounter(int count){
       restartTimers();
       dataStore.updateExerciseCount(count);
        exerciseCounter.postValue(count);
        goToNewState(State.COUNTING);
    }

    private synchronized void goToNewState(State newState){
        currentState.postValue(newState);
    }

    public LiveData<State> getCurrentState(){
        return currentState;
    }

    public LiveData<String> getExerciseName() {
        return exerciseName;
    }

    public LiveData<Integer> getCounter() {
        return exerciseCounter;
    }
}
