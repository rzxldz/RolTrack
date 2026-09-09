package com.example.roltrack.ui.workout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roltrack.R;
import com.example.roltrack.data.entity.SavedWorkout;
import com.example.roltrack.data.entity.UserProfile;
import com.example.roltrack.viewmodel.ProfileViewModel;
import com.example.roltrack.viewmodel.SavedWorkoutViewModel;
import com.example.roltrack.workout.WorkoutDay;
import com.example.roltrack.workout.WorkoutGenerator;
import com.example.roltrack.workout.WorkoutPlan;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkoutFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private SavedWorkoutViewModel savedWorkoutViewModel;

    private TextView tvWorkoutTitle;
    private TextView tvWorkoutType;
    private TextView tvWorkoutLevel;
    private TextView tvWorkoutFrequency;
    private TextView tvWorkoutDescription;
    private RecyclerView recyclerWorkoutDays;

    private WorkoutDayAdapter adapter;

    private ChipGroup chipGroupGoal;
    private ChipGroup chipGroupType;
    private ChipGroup chipGroupLevel;
    private MaterialButton btnGenerateWorkout;
    private MaterialButton btnSaveWorkout;

    private UserProfile currentProfile;
    private WorkoutPlan currentPlan;

    public WorkoutFragment() {
        super(R.layout.fragment_workout);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        savedWorkoutViewModel = new ViewModelProvider(this).get(SavedWorkoutViewModel.class);

        tvWorkoutTitle = view.findViewById(R.id.tvWorkoutTitle);
        tvWorkoutType = view.findViewById(R.id.tvWorkoutType);
        tvWorkoutLevel = view.findViewById(R.id.tvWorkoutLevel);
        tvWorkoutFrequency = view.findViewById(R.id.tvWorkoutFrequency);
        tvWorkoutDescription = view.findViewById(R.id.tvWorkoutDescription);
        recyclerWorkoutDays = view.findViewById(R.id.recyclerWorkoutDays);

        chipGroupGoal = view.findViewById(R.id.chipGroupGoal);
        chipGroupType = view.findViewById(R.id.chipGroupType);
        chipGroupLevel = view.findViewById(R.id.chipGroupLevel);
        btnGenerateWorkout = view.findViewById(R.id.btnGenerateWorkout);
        btnSaveWorkout = view.findViewById(R.id.btnSaveWorkout);

        adapter = new WorkoutDayAdapter();
        recyclerWorkoutDays.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerWorkoutDays.setAdapter(adapter);
        recyclerWorkoutDays.setNestedScrollingEnabled(false);

        profileViewModel.getUserProfile().observe(getViewLifecycleOwner(), profile -> {
            currentProfile = profile;
            showDefaultWorkout(profile);
        });

        btnGenerateWorkout.setOnClickListener(v -> generateManualWorkout());
        btnSaveWorkout.setOnClickListener(v -> saveCurrentWorkout());

        android.view.animation.Animation animation =
                android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in);
        view.startAnimation(animation);
    }

    private void showDefaultWorkout(UserProfile profile) {
        if (profile == null) {
            tvWorkoutTitle.setText("No hay perfil");
            tvWorkoutType.setText("Primero crea tu perfil");
            tvWorkoutLevel.setText("");
            tvWorkoutFrequency.setText("");
            tvWorkoutDescription.setText("");
            adapter.setDays(new ArrayList<>());
            currentPlan = null;
            return;
        }

        WorkoutPlan plan = WorkoutGenerator.generate(
                profile.getWeight(),
                profile.getHeight(),
                profile.getAge(),
                profile.getMainGoal()
        );

        showWorkout(plan);
    }

    private void generateManualWorkout() {
        if (currentProfile == null) {
            Toast.makeText(requireContext(), "Primero crea tu perfil", Toast.LENGTH_SHORT).show();
            return;
        }

        String selectedGoal = getSelectedGoal();
        String selectedType = getSelectedType();
        String selectedLevel = getSelectedLevel();

        WorkoutPlan plan = WorkoutGenerator.generateAdvanced(
                currentProfile.getWeight(),
                currentProfile.getHeight(),
                currentProfile.getAge(),
                currentProfile.getMainGoal(),
                selectedGoal,
                selectedType,
                selectedLevel
        );

        showWorkout(plan);
    }

    private String getSelectedGoal() {
        if (chipGroupGoal == null) return "Automático";
        int id = chipGroupGoal.getCheckedChipId();

        if (id == R.id.chipGoalLoss) return "Bajar grasa";
        if (id == R.id.chipGoalMuscle) return "Ganar músculo";
        if (id == R.id.chipGoalGeneral) return "Condición general";
        return "Automático";
    }

    private String getSelectedType() {
        if (chipGroupType == null) return "Automático";
        int id = chipGroupType.getCheckedChipId();

        if (id == R.id.chipTypeGym) return "Gym";
        if (id == R.id.chipTypeCalisthenics) return "Calistenia";
        return "Automático";
    }

    private String getSelectedLevel() {
        if (chipGroupLevel == null) return "Automático";
        int id = chipGroupLevel.getCheckedChipId();

        if (id == R.id.chipLevelBeginner) return "Principiante";
        if (id == R.id.chipLevelIntermediate) return "Intermedio";
        if (id == R.id.chipLevelAdvanced) return "Avanzado";
        return "Automático";
    }

    private void showWorkout(WorkoutPlan plan) {
        if (plan == null) return;

        currentPlan = plan;

        tvWorkoutTitle.setText(plan.getTitle());
        tvWorkoutType.setText("Tipo: " + plan.getTrainingType());
        tvWorkoutLevel.setText("Nivel: " + plan.getLevel());
        tvWorkoutFrequency.setText("Frecuencia: " + plan.getFrequency());
        tvWorkoutDescription.setText(plan.getDescription());

        List<WorkoutDay> days = plan.getDays() == null ? Collections.emptyList() : plan.getDays();
        adapter.setDays(days);
    }

    private void saveCurrentWorkout() {
        if (currentPlan == null) {
            Toast.makeText(requireContext(), "Primero genera una rutina", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder routineBuilder = new StringBuilder();

        if (currentPlan.getDays() != null) {
            for (WorkoutDay day : currentPlan.getDays()) {
                routineBuilder.append(day.getDayTitle())
                        .append("\n")
                        .append(day.getExercises())
                        .append("\n\n");
            }
        }

        SavedWorkout savedWorkout = new SavedWorkout(
                currentPlan.getTitle(),
                currentPlan.getTrainingType(),
                currentPlan.getLevel(),
                currentPlan.getFrequency(),
                currentPlan.getDescription(),
                routineBuilder.toString().trim()
        );

        savedWorkoutViewModel.insert(savedWorkout);
        Toast.makeText(requireContext(), "Rutina guardada", Toast.LENGTH_SHORT).show();
    }
}