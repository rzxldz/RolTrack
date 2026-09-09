package com.example.roltrack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roltrack.data.entity.SavedMealPlan;
import com.example.roltrack.data.entity.SavedWorkout;
import com.example.roltrack.data.entity.UserProfile;
import com.example.roltrack.data.repository.ExerciseRepository;
import com.example.roltrack.data.repository.MealRepository;
import com.example.roltrack.data.repository.SavedMealPlanRepository;
import com.example.roltrack.data.repository.SavedWorkoutRepository;
import com.example.roltrack.data.repository.SleepRepository;
import com.example.roltrack.data.repository.UserRepository;
import com.example.roltrack.data.repository.WaterRepository;

public class DashboardViewModel extends AndroidViewModel {

    private final WaterRepository waterRepository;
    private final MealRepository mealRepository;
    private final SleepRepository sleepRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;
    private final SavedWorkoutRepository savedWorkoutRepository;
    private final SavedMealPlanRepository savedMealPlanRepository;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        waterRepository = new WaterRepository(application);
        mealRepository = new MealRepository(application);
        sleepRepository = new SleepRepository(application);
        exerciseRepository = new ExerciseRepository(application);
        userRepository = new UserRepository(application);
        savedWorkoutRepository = new SavedWorkoutRepository(application);
        savedMealPlanRepository = new SavedMealPlanRepository(application);
    }

    public LiveData<Integer> getTotalWaterByDate(String date) {
        return waterRepository.getTotalWaterByDate(date);
    }

    public LiveData<Integer> getTotalCaloriesByDate(String date) {
        return mealRepository.getTotalCaloriesByDate(date);
    }

    public LiveData<Double> getSleepHoursByDate(String date) {
        return sleepRepository.getSleepHoursByDate(date);
    }

    public LiveData<Integer> getExerciseMinutesByDate(String date) {
        return exerciseRepository.getExerciseMinutesByDate(date);
    }

    public LiveData<UserProfile> getUserProfile() {
        return userRepository.getUserProfile();
    }

    public LiveData<SavedWorkout> getLatestSavedWorkout() {
        return savedWorkoutRepository.getLatestSavedWorkout();
    }

    public LiveData<SavedMealPlan> getLatestSavedMealPlan() {
        return savedMealPlanRepository.getLatestSavedMealPlan();
    }
}