package com.example.roltrack.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roltrack.data.entity.SavedWorkout;

import java.util.List;

@Dao
public interface SavedWorkoutDao {

    @Insert
    void insert(SavedWorkout savedWorkout);

    @Query("SELECT * FROM saved_workouts ORDER BY id DESC")
    LiveData<List<SavedWorkout>> getAllSavedWorkouts();

    @Query("SELECT * FROM saved_workouts ORDER BY id DESC LIMIT 1")
    LiveData<SavedWorkout> getLatestSavedWorkout();

    @Query("DELETE FROM saved_workouts")
    void deleteAll();
}