package com.example.roltrack.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roltrack.data.entity.WaterLog;

import java.util.List;

@Dao
public interface WaterLogDao {

    @Insert
    void insert(WaterLog waterLog);

    @Query("SELECT * FROM water_logs WHERE date = :date")
    LiveData<List<WaterLog>> getWaterLogsByDate(String date);

    @Query("SELECT COALESCE(SUM(amountMl), 0) FROM water_logs WHERE date = :date")
    LiveData<Integer> getTotalWaterByDate(String date);

    @Query("SELECT COALESCE(SUM(amountMl), 0) FROM water_logs WHERE date = :date")
    int getTotalWaterByDateSync(String date);
}