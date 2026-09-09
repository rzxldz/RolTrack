package com.example.roltrack.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meal_logs")
public class MealLog {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String date;
    private String mealType;
    private String foodName;
    private int calories;
    private String healthyLevel;

    public MealLog(String date, String mealType, String foodName, int calories, String healthyLevel) {
        this.date = date;
        this.mealType = mealType;
        this.foodName = foodName;
        this.calories = calories;
        this.healthyLevel = healthyLevel;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getMealType() { return mealType; }
    public void setMealType(String mealType) { this.mealType = mealType; }
    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }
    public int getCalories() { return calories; }
    public void setCalories(int calories) { this.calories = calories; }
    public String getHealthyLevel() { return healthyLevel; }
    public void setHealthyLevel(String healthyLevel) { this.healthyLevel = healthyLevel; }
}