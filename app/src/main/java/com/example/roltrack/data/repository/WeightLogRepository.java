package com.example.roltrack.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.roltrack.data.dao.WeightLogDao;
import com.example.roltrack.data.db.AppDatabase;
import com.example.roltrack.data.entity.WeightLog;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeightLogRepository {

    private final WeightLogDao weightLogDao;
    private final ExecutorService executorService;

    public WeightLogRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        weightLogDao = db.weightLogDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(WeightLog weightLog) {
        executorService.execute(() -> weightLogDao.insert(weightLog));
    }

    public LiveData<List<WeightLog>> getAllWeightLogs() {
        return weightLogDao.getAllWeightLogs();
    }

    public LiveData<WeightLog> getLatestWeightLog() {
        return weightLogDao.getLatestWeightLog();
    }
}