package com.example.roltrack.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roltrack.data.entity.SavedMealPlan;

@Dao
public interface SavedMealPlanDao {

    @Insert
    void insert(SavedMealPlan savedMealPlan);

    @Query("SELECT * FROM saved_meal_plans ORDER BY id DESC LIMIT 1")
    LiveData<SavedMealPlan> getLatestSavedMealPlan();

    @Query("DELETE FROM saved_meal_plans")
    void deleteAll();
}