package com.example.roltrack.ui.exercise;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roltrack.R;
import com.example.roltrack.data.entity.ExerciseLog;
import com.example.roltrack.utils.DatePickerUtils;
import com.example.roltrack.utils.DateUtils;
import com.example.roltrack.viewmodel.ExerciseViewModel;

public class ExerciseFragment extends Fragment {

    private ExerciseViewModel exerciseViewModel;
    private AutoCompleteTextView actvExerciseType, actvIntensity;
    private EditText etDurationMinutes, etEstimatedCalories;
    private TextView tvExerciseTotal, tvSelectedExerciseDate, tvExerciseEmpty;
    private RecyclerView recyclerExerciseLogs;
    private ExerciseLogAdapter adapter;
    private String selectedDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);

        actvExerciseType = view.findViewById(R.id.actvExerciseType);
        actvIntensity = view.findViewById(R.id.actvIntensity);
        etDurationMinutes = view.findViewById(R.id.etDurationMinutes);
        etEstimatedCalories = view.findViewById(R.id.etEstimatedCalories);

        tvExerciseTotal = view.findViewById(R.id.tvExerciseTotal);
        tvSelectedExerciseDate = view.findViewById(R.id.tvSelectedExerciseDate);
        tvExerciseEmpty = view.findViewById(R.id.tvExerciseEmpty);

        recyclerExerciseLogs = view.findViewById(R.id.recyclerExerciseLogs);
        Button btnSaveExercise = view.findViewById(R.id.btnSaveExercise);
        Button btnPickExerciseDate = view.findViewById(R.id.btnPickExerciseDate);

        adapter = new ExerciseLogAdapter();
        recyclerExerciseLogs.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerExerciseLogs.setAdapter(adapter);

        String[] types = {"Caminar", "Correr", "Gym", "Bici", "Yoga"};
        String[] intensities = {"Baja", "Media", "Alta"};

        actvExerciseType.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, types));
        actvIntensity.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, intensities));

        selectedDate = DateUtils.getTodayDate();
        tvSelectedExerciseDate.setText("Fecha: " + selectedDate);

        btnSaveExercise.setOnClickListener(v -> saveExercise());

        btnPickExerciseDate.setOnClickListener(v ->
                DatePickerUtils.showDatePicker(requireContext(), date -> {
                    selectedDate = date;
                    tvSelectedExerciseDate.setText("Fecha: " + selectedDate);
                    observeExercise();
                })
        );

        observeExercise();
    }

    private void observeExercise() {
        exerciseViewModel.getExerciseLogsByDate(selectedDate).observe(getViewLifecycleOwner(), logs -> {
            adapter.setExerciseLogs(logs);

            if (logs == null || logs.isEmpty()) {
                tvExerciseEmpty.setVisibility(View.VISIBLE);
                recyclerExerciseLogs.setVisibility(View.GONE);
            } else {
                tvExerciseEmpty.setVisibility(View.GONE);
                recyclerExerciseLogs.setVisibility(View.VISIBLE);
            }
        });
    }

    private void saveExercise() {
        String type = actvExerciseType.getText().toString().trim();
        String intensity = actvIntensity.getText().toString().trim();
        String duration = etDurationMinutes.getText().toString().trim();
        String calories = etEstimatedCalories.getText().toString().trim();

        if (TextUtils.isEmpty(type) || TextUtils.isEmpty(intensity) ||
                TextUtils.isEmpty(duration) || TextUtils.isEmpty(calories)) {
            Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        ExerciseLog log = new ExerciseLog(
                selectedDate,
                type,
                Integer.parseInt(duration),
                intensity,
                Integer.parseInt(calories)
        );

        exerciseViewModel.insert(log);

        Toast.makeText(requireContext(), "Ejercicio guardado", Toast.LENGTH_SHORT).show();
    }
}