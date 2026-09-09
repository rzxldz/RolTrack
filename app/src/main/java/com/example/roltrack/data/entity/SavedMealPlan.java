package com.example.roltrack.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "saved_meal_plans")
public class SavedMealPlan {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String caloriesTarget;
    private String proteinTarget;
    private String description;
    private String mealsText;

    public SavedMealPlan(String title, String caloriesTarget, String proteinTarget,
                         String description, String mealsText) {
        this.title = title;
        this.caloriesTarget = caloriesTarget;
        this.proteinTarget = proteinTarget;
        this.description = description;
        this.mealsText = mealsText;
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

    public String getCaloriesTarget() {
        return caloriesTarget;
    }

    public String getProteinTarget() {
        return proteinTarget;
    }

    public String getDescription() {
        return description;
    }

    public String getMealsText() {
        return mealsText;
    }
}