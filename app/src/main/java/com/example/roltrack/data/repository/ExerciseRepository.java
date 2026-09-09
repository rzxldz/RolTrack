package com.example.roltrack.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.roltrack.data.dao.ExerciseLogDao;
import com.example.roltrack.data.db.AppDatabase;
import com.example.roltrack.data.entity.ExerciseLog;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExerciseRepository {

    private final ExerciseLogDao exerciseLogDao;
    private final ExecutorService executorService;

    public ExerciseRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        exerciseLogDao = db.exerciseLogDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(ExerciseLog exerciseLog) {
        executorService.execute(() -> exerciseLogDao.insert(exerciseLog));
    }

    public LiveData<List<ExerciseLog>> getExerciseLogsByDate(String date) {
        return exerciseLogDao.getExerciseLogsByDate(date);
    }

    public LiveData<Integer> getExerciseMinutesByDate(String date) {
        return exerciseLogDao.getExerciseMinutesByDate(date);
    }

    public int getExerciseMinutesByDateSync(String date) {
        return exerciseLogDao.getExerciseMinutesByDateSync(date);
    }
}