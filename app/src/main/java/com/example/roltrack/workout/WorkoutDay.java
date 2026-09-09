package com.example.roltrack.workout;

public class WorkoutDay {

    private final String dayTitle;
    private final String exercises;

    public WorkoutDay(String dayTitle, String exercises) {
        this.dayTitle = dayTitle;
        this.exercises = exercises;
    }

    public String getDayTitle() {
        return dayTitle;
    }

    public String getExercises() {
        return exercises;
    }
}