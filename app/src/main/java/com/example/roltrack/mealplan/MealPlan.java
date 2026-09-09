package com.example.roltrack.mealplan;

import java.util.List;

public class MealPlan {

    private final String title;
    private final String caloriesTarget;
    private final String proteinTarget;
    private final String description;
    private final List<MealSuggestion> meals;

    public MealPlan(String title, String caloriesTarget, String proteinTarget,
                    String description, List<MealSuggestion> meals) {
        this.title = title;
        this.caloriesTarget = caloriesTarget;
        this.proteinTarget = proteinTarget;
        this.description = description;
        this.meals = meals;
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

    public List<MealSuggestion> getMeals() {
        return meals;
    }
}