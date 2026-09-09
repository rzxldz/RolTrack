package com.example.roltrack.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "saved_workouts")
public class SavedWorkout {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String trainingType;
    private String level;
    private String frequency;
    private String description;
    private String routineText;

    public SavedWorkout(String title, String trainingType, String level,
                        String frequency, String description, String routineText) {
        this.title = title;
        this.trainingType = trainingType;
        this.level = level;
        this.frequency = frequency;
        this.description = description;
        this.routineText = routineText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRoutineText() {
        return routineText;
    }
}