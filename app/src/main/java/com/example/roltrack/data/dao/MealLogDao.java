package com.example.roltrack.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roltrack.data.entity.MealLog;

import java.util.List;

@Dao
public interface MealLogDao {

    @Insert
    void insert(MealLog mealLog);

    @Query("SELECT * FROM meal_logs WHERE date = :date ORDER BY id DESC")
    LiveData<List<MealLog>> getMealsByDate(String date);

    @Query("SELECT COALESCE(SUM(calories), 0) FROM meal_logs WHERE date = :date")
    LiveData<Integer> getTotalCaloriesByDate(String date);

    @Query("SELECT COALESCE(SUM(calories), 0) FROM meal_logs WHERE date = :date")
    int getTotalCaloriesByDateSync(String date);
}