package com.example.roltrack.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.roltrack.data.dao.SavedWorkoutDao;
import com.example.roltrack.data.db.AppDatabase;
import com.example.roltrack.data.entity.SavedWorkout;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SavedWorkoutRepository {

    private final SavedWorkoutDao savedWorkoutDao;
    private final ExecutorService executorService;

    public SavedWorkoutRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        savedWorkoutDao = db.savedWorkoutDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(SavedWorkout savedWorkout) {
        executorService.execute(() -> savedWorkoutDao.insert(savedWorkout));
    }

    public LiveData<List<SavedWorkout>> getAllSavedWorkouts() {
        return savedWorkoutDao.getAllSavedWorkouts();
    }

    public LiveData<SavedWorkout> getLatestSavedWorkout() {
        return savedWorkoutDao.getLatestSavedWorkout();
    }

    public void deleteAll() {
        executorService.execute(savedWorkoutDao::deleteAll);
    }
}