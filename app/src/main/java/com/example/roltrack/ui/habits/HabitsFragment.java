package com.example.roltrack.ui.habits;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roltrack.R;
import com.example.roltrack.running.RunHistoryFragment;
import com.example.roltrack.running.RunTrackingFragment;
import com.example.roltrack.ui.exercise.ExerciseFragment;
import com.example.roltrack.ui.meal.MealFragment;
import com.example.roltrack.ui.mealplan.MealPlanFragment;
import com.example.roltrack.ui.mealplan.SavedMealPlanFragment;
import com.example.roltrack.ui.progress.PhysicalProgressFragment;
import com.example.roltrack.ui.sleep.SleepFragment;
import com.example.roltrack.ui.water.WaterFragment;
import com.example.roltrack.ui.workout.SavedWorkoutFragment;
import com.example.roltrack.ui.workout.WorkoutFragment;

public class HabitsFragment extends Fragment {

    public HabitsFragment() {
        super(R.layout.fragment_habits);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnWater = view.findViewById(R.id.btnWaterHabit);
        Button btnMeal = view.findViewById(R.id.btnMealHabit);
        Button btnSleep = view.findViewById(R.id.btnSleepHabit);
        Button btnExercise = view.findViewById(R.id.btnExerciseHabit);
        Button btnWorkout = view.findViewById(R.id.btnWorkoutHabit);
        Button btnSavedWorkout = view.findViewById(R.id.btnSavedWorkoutHabit);
        Button btnMealPlan = view.findViewById(R.id.btnMealPlanHabit);
        Button btnSavedMealPlan = view.findViewById(R.id.btnSavedMealPlanHabit);
        Button btnPhysicalProgress = view.findViewById(R.id.btnPhysicalProgressHabit);
        Button btnRunning = view.findViewById(R.id.btnRunningHabit);
        Button btnRunHistory = view.findViewById(R.id.btnRunHistoryHabit);

        btnWater.setOnClickListener(v -> openFragment(new WaterFragment()));
        btnMeal.setOnClickListener(v -> openFragment(new MealFragment()));
        btnSleep.setOnClickListener(v -> openFragment(new SleepFragment()));
        btnExercise.setOnClickListener(v -> openFragment(new ExerciseFragment()));
        btnWorkout.setOnClickListener(v -> openFragment(new WorkoutFragment()));
        btnSavedWorkout.setOnClickListener(v -> openFragment(new SavedWorkoutFragment()));
        btnMealPlan.setOnClickListener(v -> openFragment(new MealPlanFragment()));
        btnSavedMealPlan.setOnClickListener(v -> openFragment(new SavedMealPlanFragment()));
        btnPhysicalProgress.setOnClickListener(v -> openFragment(new PhysicalProgressFragment()));
        btnRunning.setOnClickListener(v -> openFragment(new RunTrackingFragment()));
        btnRunHistory.setOnClickListener(v -> openFragment(new RunHistoryFragment()));
    }

    private void openFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}