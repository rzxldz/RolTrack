package com.example.roltrack.ui.mealplan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roltrack.R;
import com.example.roltrack.mealplan.MealSuggestion;

import java.util.ArrayList;
import java.util.List;

public class MealSuggestionAdapter extends RecyclerView.Adapter<MealSuggestionAdapter.MealSuggestionViewHolder> {

    private List<MealSuggestion> meals = new ArrayList<>();

    @NonNull
    @Override
    public MealSuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meal_suggestion, parent, false);
        return new MealSuggestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealSuggestionViewHolder holder, int position) {
        MealSuggestion meal = meals.get(position);
        holder.tvMealTime.setText(meal.getMealTime());
        holder.tvMealTitle.setText(meal.getMealTitle());
        holder.tvMealDetails.setText(meal.getDetails());
    }

    @Override
    public int getItemCount() {
        return meals == null ? 0 : meals.size();
    }

    public void setMeals(List<MealSuggestion> meals) {
        this.meals = meals == null ? new ArrayList<>() : meals;
        notifyDataSetChanged();
    }

    static class MealSuggestionViewHolder extends RecyclerView.ViewHolder {
        TextView tvMealTime, tvMealTitle, tvMealDetails;

        public MealSuggestionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMealTime = itemView.findViewById(R.id.tvMealTime);
            tvMealTitle = itemView.findViewById(R.id.tvMealTitle);
            tvMealDetails = itemView.findViewById(R.id.tvMealDetails);
        }
    }
}