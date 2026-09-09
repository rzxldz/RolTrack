package com.example.roltrack.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roltrack.data.entity.ExerciseLog;

import java.util.List;

@Dao
public interface ExerciseLogDao {

    @Insert
    void insert(ExerciseLog exerciseLog);

    @Query("SELECT * FROM exercise_logs WHERE date = :date ORDER BY id DESC")
    LiveData<List<ExerciseLog>> getExerciseLogsByDate(String date);

    @Query("SELECT COALESCE(SUM(durationMinutes), 0) FROM exercise_logs WHERE date = :date")
    LiveData<Integer> getExerciseMinutesByDate(String date);

    @Query("SELECT COALESCE(SUM(durationMinutes), 0) FROM exercise_logs WHERE date = :date")
    int getExerciseMinutesByDateSync(String date);
}