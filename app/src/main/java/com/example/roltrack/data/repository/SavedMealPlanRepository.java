package com.example.roltrack.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.roltrack.data.dao.SavedMealPlanDao;
import com.example.roltrack.data.db.AppDatabase;
import com.example.roltrack.data.entity.SavedMealPlan;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SavedMealPlanRepository {

    private final SavedMealPlanDao savedMealPlanDao;
    private final ExecutorService executorService;

    public SavedMealPlanRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        savedMealPlanDao = db.savedMealPlanDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(SavedMealPlan savedMealPlan) {
        executorService.execute(() -> savedMealPlanDao.insert(savedMealPlan));
    }

    public LiveData<SavedMealPlan> getLatestSavedMealPlan() {
        return savedMealPlanDao.getLatestSavedMealPlan();
    }

    public void deleteAll() {
        executorService.execute(savedMealPlanDao::deleteAll);
    }
}