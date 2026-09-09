package com.example.roltrack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.roltrack.data.repository.ExerciseRepository;
import com.example.roltrack.data.repository.MealRepository;
import com.example.roltrack.data.repository.SleepRepository;
import com.example.roltrack.data.repository.WaterRepository;

public class StatsViewModel extends AndroidViewModel {

    private final WaterRepository waterRepository;
    private final MealRepository mealRepository;
    private final SleepRepository sleepRepository;
    private final ExerciseRepository exerciseRepository;

    public StatsViewModel(@NonNull Application application) {
        super(application);
        waterRepository = new WaterRepository(application);
        mealRepository = new MealRepository(application);
        sleepRepository = new SleepRepository(application);
        exerciseRepository = new ExerciseRepository(application);
    }

    public int getTotalWaterByDateSync(String date) {
        return waterRepository.getTotalWaterByDateSync(date);
    }

    public int getTotalCaloriesByDateSync(String date) {
        return mealRepository.getTotalCaloriesByDateSync(date);
    }

    public double getSleepHoursByDateSync(String date) {
        return sleepRepository.getSleepHoursByDateSync(date);
    }

    public int getExerciseMinutesByDateSync(String date) {
        return exerciseRepository.getExerciseMinutesByDateSync(date);
    }
}