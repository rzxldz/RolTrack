package com.example.roltrack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roltrack.data.entity.SavedWorkout;
import com.example.roltrack.data.repository.SavedWorkoutRepository;

import java.util.List;

public class SavedWorkoutViewModel extends AndroidViewModel {

    private final SavedWorkoutRepository repository;

    public SavedWorkoutViewModel(@NonNull Application application) {
        super(application);
        repository = new SavedWorkoutRepository(application);
    }

    public void insert(SavedWorkout savedWorkout) {
        repository.insert(savedWorkout);
    }

    public LiveData<List<SavedWorkout>> getAllSavedWorkouts() {
        return repository.getAllSavedWorkouts();
    }

    public LiveData<SavedWorkout> getLatestSavedWorkout() {
        return repository.getLatestSavedWorkout();
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}