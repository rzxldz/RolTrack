package com.example.roltrack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roltrack.data.entity.ExerciseLog;
import com.example.roltrack.data.repository.ExerciseRepository;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {

    private final ExerciseRepository repository;

    public ExerciseViewModel(@NonNull Application application) {
        super(application);
        repository = new ExerciseRepository(application);
    }

    public void insert(ExerciseLog exerciseLog) {
        repository.insert(exerciseLog);
    }

    public LiveData<List<ExerciseLog>> getExerciseLogsByDate(String date) {
        return repository.getExerciseLogsByDate(date);
    }

    public LiveData<Integer> getExerciseMinutesByDate(String date) {
        return repository.getExerciseMinutesByDate(date);
    }
}