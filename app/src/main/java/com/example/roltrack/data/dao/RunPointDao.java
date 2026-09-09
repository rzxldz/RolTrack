package com.example.roltrack.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roltrack.data.entity.RunPoint;

import java.util.List;

@Dao
public interface RunPointDao {

    @Insert
    void insertAll(List<RunPoint> points);

    @Query("SELECT * FROM run_points WHERE sessionId = :sessionId ORDER BY timestamp ASC")
    List<RunPoint> getPointsForSession(int sessionId);
}