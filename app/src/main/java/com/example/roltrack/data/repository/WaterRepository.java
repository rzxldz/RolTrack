package com.example.roltrack.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.roltrack.data.dao.WaterLogDao;
import com.example.roltrack.data.db.AppDatabase;
import com.example.roltrack.data.entity.WaterLog;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WaterRepository {

    private final WaterLogDao waterLogDao;
    private final ExecutorService executorService;

    public WaterRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        waterLogDao = db.waterLogDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(WaterLog waterLog) {
        executorService.execute(() -> waterLogDao.insert(waterLog));
    }

    public LiveData<List<WaterLog>> getWaterLogsByDate(String date) {
        return waterLogDao.getWaterLogsByDate(date);
    }

    public LiveData<Integer> getTotalWaterByDate(String date) {
        return waterLogDao.getTotalWaterByDate(date);
    }

    public int getTotalWaterByDateSync(String date) {
        return waterLogDao.getTotalWaterByDateSync(date);
    }
}