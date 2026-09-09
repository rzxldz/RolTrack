package com.example.roltrack.ui.mealplan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roltrack.R;
import com.example.roltrack.data.entity.SavedMealPlan;
import com.example.roltrack.data.entity.UserProfile;
import com.example.roltrack.mealplan.MealPlan;
import com.example.roltrack.mealplan.MealPlanGenerator;
import com.example.roltrack.mealplan.MealSuggestion;
import com.example.roltrack.viewmodel.ProfileViewModel;
import com.example.roltrack.viewmodel.SavedMealPlanViewModel;
import com.google.android.material.button.MaterialButton;

public class MealPlanFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private SavedMealPlanViewModel savedMealPlanViewModel;

    private TextView tvMealPlanTitle;
    private TextView tvMealPlanCalories;
    private TextView tvMealPlanProtein;
    private TextView tvMealPlanDescription;
    private RecyclerView recyclerMealSuggestions;
    private MaterialButton btnSaveMealPlan;

    private MealSuggestionAdapter adapter;
    private MealPlan currentPlan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        savedMealPlanViewModel = new ViewModelProvider(this).get(SavedMealPlanViewModel.class);

        tvMealPlanTitle = view.findViewById(R.id.tvMealPlanTitle);
        tvMealPlanCalories = view.findViewById(R.id.tvMealPlanCalories);
        tvMealPlanProtein = view.findViewById(R.id.tvMealPlanProtein);
        tvMealPlanDescription = view.findViewById(R.id.tvMealPlanDescription);
        recyclerMealSuggestions = view.findViewById(R.id.recyclerMealSuggestions);
        btnSaveMealPlan = view.findViewById(R.id.btnSaveMealPlan);

        adapter = new MealSuggestionAdapter();
        recyclerMealSuggestions.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerMealSuggestions.setAdapter(adapter);
        recyclerMealSuggestions.setNestedScrollingEnabled(false);

        profileViewModel.getUserProfile().observe(getViewLifecycleOwner(), this::showMealPlan);

        btnSaveMealPlan.setOnClickListener(v -> saveCurrentMealPlan());
    }

    private void showMealPlan(UserProfile profile) {
        if (profile == null) {
            tvMealPlanTitle.setText("No hay perfil");
            tvMealPlanCalories.setText("");
            tvMealPlanProtein.setText("");
            tvMealPlanDescription.setText("Primero crea tu perfil para generar un plan.");
            adapter.setMeals(null);
            return;
        }

        MealPlan plan = MealPlanGenerator.generate(
                profile.getWeight(),
                profile.getHeight(),
                profile.getAge(),
                profile.getMainGoal()
        );

        currentPlan = plan;

        tvMealPlanTitle.setText(plan.getTitle());
        tvMealPlanCalories.setText("Calorías objetivo: " + plan.getCaloriesTarget());
        tvMealPlanProtein.setText("Proteína objetivo: " + plan.getProteinTarget());
        tvMealPlanDescription.setText(plan.getDescription());
        adapter.setMeals(plan.getMeals());

        android.view.animation.Animation animation =
                android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up);

        tvMealPlanTitle.startAnimation(animation);
        tvMealPlanCalories.startAnimation(animation);
        tvMealPlanProtein.startAnimation(animation);
        tvMealPlanDescription.startAnimation(animation);
        recyclerMealSuggestions.startAnimation(animation);
    }

    private void saveCurrentMealPlan() {
        if (currentPlan == null) {
            Toast.makeText(requireContext(), "Primero genera un plan", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder mealsBuilder = new StringBuilder();

        for (MealSuggestion meal : currentPlan.getMeals()) {
            mealsBuilder.append(meal.getMealTime())
                    .append(" - ")
                    .append(meal.getMealTitle())
                    .append("\n")
                    .append(meal.getDetails())
                    .append("\n\n");
        }

        SavedMealPlan savedMealPlan = new SavedMealPlan(
                currentPlan.getTitle(),
                currentPlan.getCaloriesTarget(),
                currentPlan.getProteinTarget(),
                currentPlan.getDescription(),
                mealsBuilder.toString().trim()
        );

        savedMealPlanViewModel.insert(savedMealPlan);
        Toast.makeText(requireContext(), "Plan alimenticio guardado", Toast.LENGTH_SHORT).show();
    }
}