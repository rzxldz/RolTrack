package com.example.roltrack.ui.workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.roltrack.R;
import com.example.roltrack.data.entity.SavedWorkout;
import com.example.roltrack.viewmodel.SavedWorkoutViewModel;

public class SavedWorkoutFragment extends Fragment {

    private SavedWorkoutViewModel savedWorkoutViewModel;

    private TextView tvSavedWorkoutTitle;
    private TextView tvSavedWorkoutType;
    private TextView tvSavedWorkoutLevel;
    private TextView tvSavedWorkoutFrequency;
    private TextView tvSavedWorkoutDescription;
    private TextView tvSavedWorkoutRoutine;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved_workout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        savedWorkoutViewModel = new ViewModelProvider(this).get(SavedWorkoutViewModel.class);

        tvSavedWorkoutTitle = view.findViewById(R.id.tvSavedWorkoutTitle);
        tvSavedWorkoutType = view.findViewById(R.id.tvSavedWorkoutType);
        tvSavedWorkoutLevel = view.findViewById(R.id.tvSavedWorkoutLevel);
        tvSavedWorkoutFrequency = view.findViewById(R.id.tvSavedWorkoutFrequency);
        tvSavedWorkoutDescription = view.findViewById(R.id.tvSavedWorkoutDescription);
        tvSavedWorkoutRoutine = view.findViewById(R.id.tvSavedWorkoutRoutine);

        savedWorkoutViewModel.getLatestSavedWorkout().observe(getViewLifecycleOwner(), this::showSavedWorkout);
    }

    private void showSavedWorkout(SavedWorkout workout) {
        if (workout == null) {
            tvSavedWorkoutTitle.setText("No hay rutina guardada");
            tvSavedWorkoutType.setText("");
            tvSavedWorkoutLevel.setText("");
            tvSavedWorkoutFrequency.setText("");
            tvSavedWorkoutDescription.setText("Genera y guarda una rutina desde el módulo de rutina.");
            tvSavedWorkoutRoutine.setText("");
            return;
        }

        tvSavedWorkoutTitle.setText(workout.getTitle());
        tvSavedWorkoutType.setText("Tipo: " + workout.getTrainingType());
        tvSavedWorkoutLevel.setText("Nivel: " + workout.getLevel());
        tvSavedWorkoutFrequency.setText("Frecuencia: " + workout.getFrequency());
        tvSavedWorkoutDescription.setText(workout.getDescription());
        tvSavedWorkoutRoutine.setText(workout.getRoutineText());
    }
}