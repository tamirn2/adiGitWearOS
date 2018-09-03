package com.brock.adi.project;

class DataStore {

    private Workout workout = new Workout();

    public void addExerciseToWorkout(String exerciseName) {
        workout.workoutSummery.add(new Workout.Exercise(exerciseName, 0));
    }

    public void updateExerciseCount(int newCount){
        workout.getLastExercise().exerciseCount = newCount;
    }
}
