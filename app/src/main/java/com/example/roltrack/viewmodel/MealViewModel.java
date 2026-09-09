package com.example.roltrack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roltrack.data.entity.MealLog;
import com.example.roltrack.data.repository.MealRepository;

import java.util.List;

public class MealViewModel extends AndroidViewModel {

    private final MealRepository repository;

    public MealViewModel(@NonNull Application application) {
        super(application);
        repository = new MealRepository(application);
    }

    public void insert(MealLog mealLog) {
        repository.insert(mealLog);
    }

    public LiveData<List<MealLog>> getMealsByDate(String date) {
        return repository.getMealsByDate(date);
    }

    public LiveData<Integer> getTotalCaloriesByDate(String date) {
        return repository.getTotalCaloriesByDate(date);
    }
}