package com.example.roltrack.ui.mealplan;

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
import com.example.roltrack.data.entity.SavedMealPlan;
import com.example.roltrack.viewmodel.SavedMealPlanViewModel;

public class SavedMealPlanFragment extends Fragment {

    private SavedMealPlanViewModel savedMealPlanViewModel;

    private TextView tvSavedMealPlanTitle;
    private TextView tvSavedMealPlanCalories;
    private TextView tvSavedMealPlanProtein;
    private TextView tvSavedMealPlanDescription;
    private TextView tvSavedMealPlanMeals;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved_meal_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        savedMealPlanViewModel = new ViewModelProvider(this).get(SavedMealPlanViewModel.class);

        tvSavedMealPlanTitle = view.findViewById(R.id.tvSavedMealPlanTitle);
        tvSavedMealPlanCalories = view.findViewById(R.id.tvSavedMealPlanCalories);
        tvSavedMealPlanProtein = view.findViewById(R.id.tvSavedMealPlanProtein);
        tvSavedMealPlanDescription = view.findViewById(R.id.tvSavedMealPlanDescription);
        tvSavedMealPlanMeals = view.findViewById(R.id.tvSavedMealPlanMeals);

        savedMealPlanViewModel.getLatestSavedMealPlan().observe(getViewLifecycleOwner(), this::showSavedMealPlan);
    }

    private void showSavedMealPlan(SavedMealPlan plan) {
        if (plan == null) {
            tvSavedMealPlanTitle.setText("No hay plan guardado");
            tvSavedMealPlanCalories.setText("");
            tvSavedMealPlanProtein.setText("");
            tvSavedMealPlanDescription.setText("Genera y guarda un plan alimenticio primero.");
            tvSavedMealPlanMeals.setText("");
            return;
        }

        tvSavedMealPlanTitle.setText(plan.getTitle());
        tvSavedMealPlanCalories.setText("Calorías objetivo: " + plan.getCaloriesTarget());
        tvSavedMealPlanProtein.setText("Proteína objetivo: " + plan.getProteinTarget());
        tvSavedMealPlanDescription.setText(plan.getDescription());
        tvSavedMealPlanMeals.setText(plan.getMealsText());
    }
}