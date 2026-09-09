package com.example.roltrack.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.roltrack.R;
import com.example.roltrack.data.entity.SavedMealPlan;
import com.example.roltrack.data.entity.SavedWorkout;
import com.example.roltrack.data.entity.UserProfile;
import com.example.roltrack.utils.DateUtils;
import com.example.roltrack.utils.HealthUtils;
import com.example.roltrack.viewmodel.DashboardViewModel;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    private TextView tvWaterValue;
    private TextView tvCaloriesValue;
    private TextView tvSleepValue;
    private TextView tvExerciseValue;
    private TextView tvDailyScore;
    private TextView tvMotivation;
    private TextView tvDashboardWorkout;
    private TextView tvDashboardMealPlan;
    private TextView tvCompletedHabits;
    private TextView tvHabitStreakMessage;
    private TextView tvGreeting;
    private TextView tvDailyFocus;

    private ProgressBar progressWater;
    private ProgressBar progressCalories;
    private ProgressBar progressSleep;
    private ProgressBar progressExercise;

    private int waterToday = 0;
    private int caloriesToday = 0;
    private double sleepToday = 0.0;
    private int exerciseToday = 0;

    private int waterGoal = 2000;
    private int caloriesGoal = 2000;
    private int sleepGoal = 8;
    private int exerciseGoal = 60;

    private String userName = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        tvGreeting = view.findViewById(R.id.tvGreeting);
        tvDailyFocus = view.findViewById(R.id.tvDailyFocus);

        tvDailyScore = view.findViewById(R.id.tvDailyScore);
        tvMotivation = view.findViewById(R.id.tvMotivation);

        tvCompletedHabits = view.findViewById(R.id.tvCompletedHabits);
        tvHabitStreakMessage = view.findViewById(R.id.tvHabitStreakMessage);

        tvWaterValue = view.findViewById(R.id.tvWaterValue);
        tvCaloriesValue = view.findViewById(R.id.tvCaloriesValue);
        tvSleepValue = view.findViewById(R.id.tvSleepValue);
        tvExerciseValue = view.findViewById(R.id.tvExerciseValue);

        tvDashboardWorkout = view.findViewById(R.id.tvDashboardWorkout);
        tvDashboardMealPlan = view.findViewById(R.id.tvDashboardMealPlan);

        progressWater = view.findViewById(R.id.progressWater);
        progressCalories = view.findViewById(R.id.progressCalories);
        progressSleep = view.findViewById(R.id.progressSleep);
        progressExercise = view.findViewById(R.id.progressExercise);

        String today = DateUtils.getTodayDate();

        dashboardViewModel.getUserProfile().observe(getViewLifecycleOwner(), this::applyUserGoals);

        dashboardViewModel.getTotalWaterByDate(today).observe(getViewLifecycleOwner(), total -> {
            waterToday = (total == null) ? 0 : total;
            updateDashboard();
        });

        dashboardViewModel.getTotalCaloriesByDate(today).observe(getViewLifecycleOwner(), total -> {
            caloriesToday = (total == null) ? 0 : total;
            updateDashboard();
        });

        dashboardViewModel.getSleepHoursByDate(today).observe(getViewLifecycleOwner(), total -> {
            sleepToday = (total == null) ? 0.0 : total;
            updateDashboard();
        });

        dashboardViewModel.getExerciseMinutesByDate(today).observe(getViewLifecycleOwner(), total -> {
            exerciseToday = (total == null) ? 0 : total;
            updateDashboard();
        });

        dashboardViewModel.getLatestSavedWorkout().observe(getViewLifecycleOwner(), this::showSavedWorkout);
        dashboardViewModel.getLatestSavedMealPlan().observe(getViewLifecycleOwner(), this::showSavedMealPlan);

        android.view.animation.Animation animation =
                android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in);
        view.startAnimation(animation);

        updateDashboard();
    }

    private void applyUserGoals(UserProfile userProfile) {
        if (userProfile != null) {
            waterGoal = userProfile.getDailyWaterGoalMl();
            sleepGoal = userProfile.getDailySleepGoalHours();

            int weeklyExercise = userProfile.getWeeklyExerciseGoalMinutes();
            exerciseGoal = Math.max(weeklyExercise / 7, 1);

            userName = userProfile.getName() == null ? "" : userProfile.getName();
        }

        updateDashboard();
    }

    private void showSavedWorkout(SavedWorkout workout) {
        if (tvDashboardWorkout == null) return;

        if (workout == null) {
            tvDashboardWorkout.setText("Sin rutina guardada");
            return;
        }

        tvDashboardWorkout.setText(
                workout.getTitle() + " • " + workout.getTrainingType() + " • " + workout.getLevel()
        );
    }

    private void showSavedMealPlan(SavedMealPlan plan) {
        if (tvDashboardMealPlan == null) return;

        if (plan == null) {
            tvDashboardMealPlan.setText("Sin plan guardado");
            return;
        }

        tvDashboardMealPlan.setText(
                plan.getTitle() + " • " + plan.getCaloriesTarget() + " • " + plan.getProteinTarget()
        );
    }

    private void updateDashboard() {
        if (!isAdded()
                || progressWater == null
                || progressCalories == null
                || progressSleep == null
                || progressExercise == null
                || tvWaterValue == null
                || tvCaloriesValue == null
                || tvSleepValue == null
                || tvExerciseValue == null
                || tvDailyScore == null
                || tvMotivation == null
                || tvCompletedHabits == null
                || tvHabitStreakMessage == null
                || tvGreeting == null
                || tvDailyFocus == null) {
            return;
        }

        progressWater.setMax(Math.max(waterGoal, 1));
        progressCalories.setMax(Math.max(caloriesGoal, 1));
        progressSleep.setMax(Math.max(sleepGoal, 1));
        progressExercise.setMax(Math.max(exerciseGoal, 1));

        tvWaterValue.setText("Hoy: " + waterToday + " de " + waterGoal + " ml");
        tvCaloriesValue.setText("Hoy: " + caloriesToday + " de " + caloriesGoal + " kcal");
        tvSleepValue.setText(String.format("Hoy: %.1f de %d h", sleepToday, sleepGoal));
        tvExerciseValue.setText("Hoy: " + exerciseToday + " de " + exerciseGoal + " min");

        progressWater.setProgress(Math.min(waterToday, waterGoal));
        progressCalories.setProgress(Math.min(caloriesToday, caloriesGoal));
        progressSleep.setProgress((int) Math.min(sleepToday, sleepGoal));
        progressExercise.setProgress(Math.min(exerciseToday, exerciseGoal));

        int waterPercent = HealthUtils.calculatePercent(waterToday, waterGoal);
        int nutritionPercent = HealthUtils.calculatePercent(caloriesToday, caloriesGoal);
        int sleepPercent = HealthUtils.calculatePercentDouble(sleepToday, sleepGoal);
        int exercisePercent = HealthUtils.calculatePercent(exerciseToday, exerciseGoal);

        int score = HealthUtils.calculateDailyScore(
                waterPercent,
                nutritionPercent,
                sleepPercent,
                exercisePercent
        );

        tvDailyScore.setText(score + "/100");
        tvMotivation.setText(HealthUtils.getMotivationalMessage(score));

        int completedHabits = HealthUtils.countCompletedHabitsToday(
                waterToday,
                caloriesToday,
                sleepToday,
                exerciseToday
        );

        tvCompletedHabits.setText(completedHabits + " de 4 hábitos completados");
        tvHabitStreakMessage.setText(HealthUtils.getHabitStreakMessage(completedHabits));

        tvGreeting.setText(HealthUtils.getGreetingMessage(userName));
        tvDailyFocus.setText(HealthUtils.getDailyFocusMessage(score, completedHabits));
    }
}