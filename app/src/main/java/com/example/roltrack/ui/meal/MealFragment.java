package com.example.roltrack.ui.meal;

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
import com.example.roltrack.data.entity.MealLog;
import com.example.roltrack.utils.DatePickerUtils;
import com.example.roltrack.utils.DateUtils;
import com.example.roltrack.viewmodel.MealViewModel;

public class MealFragment extends Fragment {

    private MealViewModel mealViewModel;
    private AutoCompleteTextView actvMealType, actvHealthyLevel;
    private EditText etFoodName, etCalories;
    private TextView tvCaloriesTotal, tvSelectedMealDate, tvMealEmpty;
    private RecyclerView recyclerMealLogs;
    private MealLogAdapter adapter;
    private String selectedDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mealViewModel = new ViewModelProvider(this).get(MealViewModel.class);

        actvMealType = view.findViewById(R.id.actvMealType);
        actvHealthyLevel = view.findViewById(R.id.actvHealthyLevel);
        etFoodName = view.findViewById(R.id.etFoodName);
        etCalories = view.findViewById(R.id.etCalories);

        tvCaloriesTotal = view.findViewById(R.id.tvCaloriesTotal);
        tvSelectedMealDate = view.findViewById(R.id.tvSelectedMealDate);
        tvMealEmpty = view.findViewById(R.id.tvMealEmpty);

        recyclerMealLogs = view.findViewById(R.id.recyclerMealLogs);
        Button btnSaveMeal = view.findViewById(R.id.btnSaveMeal);
        Button btnPickMealDate = view.findViewById(R.id.btnPickMealDate);

        adapter = new MealLogAdapter();
        recyclerMealLogs.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerMealLogs.setAdapter(adapter);

        String[] mealTypes = {"Desayuno", "Comida", "Cena", "Snack"};
        String[] healthyLevels = {"Verde", "Amarillo", "Rojo"};

        actvMealType.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, mealTypes));
        actvHealthyLevel.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, healthyLevels));

        selectedDate = DateUtils.getTodayDate();
        tvSelectedMealDate.setText("Fecha: " + selectedDate);

        btnSaveMeal.setOnClickListener(v -> saveMeal());

        btnPickMealDate.setOnClickListener(v ->
                DatePickerUtils.showDatePicker(requireContext(), date -> {
                    selectedDate = date;
                    tvSelectedMealDate.setText("Fecha: " + selectedDate);
                    observeMeals();
                })
        );

        observeMeals();
    }

    private void observeMeals() {
        mealViewModel.getTotalCaloriesByDate(selectedDate).observe(getViewLifecycleOwner(), total -> {
            int calories = total == null ? 0 : total;
            tvCaloriesTotal.setText("Calorías: " + calories);
        });

        mealViewModel.getMealsByDate(selectedDate).observe(getViewLifecycleOwner(), mealLogs -> {
            adapter.setMealLogs(mealLogs);

            if (mealLogs == null || mealLogs.isEmpty()) {
                tvMealEmpty.setVisibility(View.VISIBLE);
                recyclerMealLogs.setVisibility(View.GONE);
            } else {
                tvMealEmpty.setVisibility(View.GONE);
                recyclerMealLogs.setVisibility(View.VISIBLE);
            }
        });
    }

    private void saveMeal() {
        String mealType = actvMealType.getText().toString().trim();
        String foodName = etFoodName.getText().toString().trim();
        String caloriesText = etCalories.getText().toString().trim();
        String healthyLevel = actvHealthyLevel.getText().toString().trim();

        if (TextUtils.isEmpty(mealType) || TextUtils.isEmpty(foodName) ||
                TextUtils.isEmpty(caloriesText) || TextUtils.isEmpty(healthyLevel)) {
            Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        MealLog mealLog = new MealLog(
                selectedDate,
                mealType,
                foodName,
                Integer.parseInt(caloriesText),
                healthyLevel
        );

        mealViewModel.insert(mealLog);

        etFoodName.setText("");
        etCalories.setText("");
        actvMealType.setText("");
        actvHealthyLevel.setText("");

        Toast.makeText(requireContext(), "Comida guardada", Toast.LENGTH_SHORT).show();
    }
}