package com.example.roltrack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roltrack.data.entity.SavedMealPlan;
import com.example.roltrack.data.repository.SavedMealPlanRepository;

public class SavedMealPlanViewModel extends AndroidViewModel {

    private final SavedMealPlanRepository repository;

    public SavedMealPlanViewModel(@NonNull Application application) {
        super(application);
        repository = new SavedMealPlanRepository(application);
    }

    public void insert(SavedMealPlan savedMealPlan) {
        repository.insert(savedMealPlan);
    }

    public LiveData<SavedMealPlan> getLatestSavedMealPlan() {
        return repository.getLatestSavedMealPlan();
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}