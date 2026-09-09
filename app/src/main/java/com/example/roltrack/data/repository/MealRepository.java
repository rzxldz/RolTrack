package com.example.roltrack.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.roltrack.data.dao.MealLogDao;
import com.example.roltrack.data.db.AppDatabase;
import com.example.roltrack.data.entity.MealLog;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MealRepository {

    private final MealLogDao mealLogDao;
    private final ExecutorService executorService;

    public MealRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mealLogDao = db.mealLogDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(MealLog mealLog) {
        executorService.execute(() -> mealLogDao.insert(mealLog));
    }

    public LiveData<List<MealLog>> getMealsByDate(String date) {
        return mealLogDao.getMealsByDate(date);
    }

    public LiveData<Integer> getTotalCaloriesByDate(String date) {
        return mealLogDao.getTotalCaloriesByDate(date);
    }

    public int getTotalCaloriesByDateSync(String date) {
        return mealLogDao.getTotalCaloriesByDateSync(date);
    }
}