package com.example.roltrack.workout;

import java.util.List;

public class WorkoutPlan {

    private final String title;
    private final String trainingType;
    private final String level;
    private final String frequency;
    private final String description;
    private final List<WorkoutDay> days;

    public WorkoutPlan(String title, String trainingType, String level, String frequency,
                       String description, List<WorkoutDay> days) {
        this.title = title;
        this.trainingType = trainingType;
        this.level = level;
        this.frequency = frequency;
        this.description = description;
        this.days = days;
    }

    public String getTitle() {
        return title;
    }

    public String getTrainingType() {
        return trainingType;
    }

    public String getLevel() {
        return level;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getDescription() {
        return description;
    }

    public List<WorkoutDay> getDays() {
        return days;
    }
}