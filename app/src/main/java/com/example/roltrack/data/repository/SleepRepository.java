package com.example.roltrack.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.roltrack.data.dao.SleepLogDao;
import com.example.roltrack.data.db.AppDatabase;
import com.example.roltrack.data.entity.SleepLog;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SleepRepository {

    private final SleepLogDao sleepLogDao;
    private final ExecutorService executorService;

    public SleepRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        sleepLogDao = db.sleepLogDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(SleepLog sleepLog) {
        executorService.execute(() -> sleepLogDao.insert(sleepLog));
    }

    public LiveData<List<SleepLog>> getSleepLogsByDate(String date) {
        return sleepLogDao.getSleepLogsByDate(date);
    }

    public LiveData<Double> getSleepHoursByDate(String date) {
        return sleepLogDao.getSleepHoursByDate(date);
    }

    public double getSleepHoursByDateSync(String date) {
        return sleepLogDao.getSleepHoursByDateSync(date);
    }
}