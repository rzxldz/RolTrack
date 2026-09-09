package com.example.roltrack.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roltrack.data.entity.Goal;

import java.util.List;

@Dao
public interface GoalDao {

    @Insert
    void insert(Goal goal);

    @Query("SELECT * FROM goals")
    List<Goal> getAllGoals();
}