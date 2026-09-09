package com.example.roltrack.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roltrack.data.entity.SleepLog;

import java.util.List;

@Dao
public interface SleepLogDao {

    @Insert
    void insert(SleepLog sleepLog);

    @Query("SELECT * FROM sleep_logs WHERE date = :date ORDER BY id DESC")
    LiveData<List<SleepLog>> getSleepLogsByDate(String date);

    @Query("SELECT COALESCE(MAX(hoursSlept), 0) FROM sleep_logs WHERE date = :date")
    LiveData<Double> getSleepHoursByDate(String date);

    @Query("SELECT COALESCE(MAX(hoursSlept), 0) FROM sleep_logs WHERE date = :date")
    double getSleepHoursByDateSync(String date);

}