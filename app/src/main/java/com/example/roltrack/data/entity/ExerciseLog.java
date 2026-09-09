package com.example.roltrack.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise_logs")
public class ExerciseLog {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String date;
    private String exerciseType;
    private int durationMinutes;
    private String intensity;
    private int estimatedCalories;

    public ExerciseLog(String date, String exerciseType, int durationMinutes, String intensity, int estimatedCalories) {
        this.date = date;
        this.exerciseType = exerciseType;
        this.durationMinutes = durationMinutes;
        this.intensity = intensity;
        this.estimatedCalories = estimatedCalories;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getExerciseType() { return exerciseType; }
    public void setExerciseType(String exerciseType) { this.exerciseType = exerciseType; }
    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
    public String getIntensity() { return intensity; }
    public void setIntensity(String intensity) { this.intensity = intensity; }
    public int getEstimatedCalories() { return estimatedCalories; }
    public void setEstimatedCalories(int estimatedCalories) { this.estimatedCalories = estimatedCalories; }
}