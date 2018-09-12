package com.brock.adi.project;

import java.util.ArrayList;

public class Workout {

    static class Exercise {

        private String exerciseName;
        int exerciseCount;

        Exercise(String name, int count)
        {
            exerciseName = name;
            exerciseCount = count;
        }
    }


    public String timeStamp = null;
    public ArrayList<Exercise> workoutSummery = null;

    public Exercise getLastExercise(){
        return workoutSummery.get(workoutSummery.size() - 1);
    }

}
