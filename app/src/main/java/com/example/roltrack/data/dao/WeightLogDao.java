package com.example.roltrack.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roltrack.data.entity.WeightLog;

import java.util.List;

@Dao
public interface WeightLogDao {

    @Insert
    void insert(WeightLog weightLog);

    @Query("SELECT * FROM weight_logs ORDER BY id DESC")
    LiveData<List<WeightLog>> getAllWeightLogs();

    @Query("SELECT * FROM weight_logs ORDER BY id DESC LIMIT 1")
    LiveData<WeightLog> getLatestWeightLog();
}