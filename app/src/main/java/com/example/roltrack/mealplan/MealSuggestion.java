package com.example.roltrack.mealplan;

public class MealSuggestion {

    private final String mealTime;
    private final String mealTitle;
    private final String details;

    public MealSuggestion(String mealTime, String mealTitle, String details) {
        this.mealTime = mealTime;
        this.mealTitle = mealTitle;
        this.details = details;
    }

    public String getMealTime() {
        return mealTime;
    }

    public String getMealTitle() {
        return mealTitle;
    }

    public String getDetails() {
        return details;
    }
}