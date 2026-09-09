package com.example.roltrack.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_profile")
public class UserProfile {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int age;
    private double weight;
    private double height;
    private String mainGoal;
    private int dailyWaterGoalMl;
    private int dailySleepGoalHours;
    private int weeklyExerciseGoalMinutes;
    private String photoUri;

    public UserProfile(String name, int age, double weight, double height, String mainGoal,
                       int dailyWaterGoalMl, int dailySleepGoalHours, int weeklyExerciseGoalMinutes,
                       String photoUri) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.mainGoal = mainGoal;
        this.dailyWaterGoalMl = dailyWaterGoalMl;
        this.dailySleepGoalHours = dailySleepGoalHours;
        this.weeklyExerciseGoalMinutes = weeklyExerciseGoalMinutes;
        this.photoUri = photoUri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getMainGoal() {
        return mainGoal;
    }

    public void setMainGoal(String mainGoal) {
        this.mainGoal = mainGoal;
    }

    public int getDailyWaterGoalMl() {
        return dailyWaterGoalMl;
    }

    public void setDailyWaterGoalMl(int dailyWaterGoalMl) {
        this.dailyWaterGoalMl = dailyWaterGoalMl;
    }

    public int getDailySleepGoalHours() {
        return dailySleepGoalHours;
    }

    public void setDailySleepGoalHours(int dailySleepGoalHours) {
        this.dailySleepGoalHours = dailySleepGoalHours;
    }

    public int getWeeklyExerciseGoalMinutes() {
        return weeklyExerciseGoalMinutes;
    }

    public void setWeeklyExerciseGoalMinutes(int weeklyExerciseGoalMinutes) {
        this.weeklyExerciseGoalMinutes = weeklyExerciseGoalMinutes;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}