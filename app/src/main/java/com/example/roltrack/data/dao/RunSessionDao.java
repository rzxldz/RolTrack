package com.example.roltrack.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roltrack.data.entity.RunSession;

import java.util.List;

@Dao
public interface RunSessionDao {

    @Insert
    long insert(RunSession session);

    @Query("SELECT * FROM run_sessions ORDER BY id DESC")
    LiveData<List<RunSession>> getAllSessions();

    @Query("SELECT * FROM run_sessions ORDER BY id DESC LIMIT 1")
    RunSession getLatestSessionSync();
}