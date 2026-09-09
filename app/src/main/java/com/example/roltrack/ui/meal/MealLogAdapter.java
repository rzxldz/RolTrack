package com.example.roltrack.ui.meal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roltrack.R;
import com.example.roltrack.data.entity.MealLog;

import java.util.ArrayList;
import java.util.List;

public class MealLogAdapter extends RecyclerView.Adapter<MealLogAdapter.MealLogViewHolder> {

    private List<MealLog> mealLogs = new ArrayList<>();

    @NonNull
    @Override
    public MealLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meal_log, parent, false);
        return new MealLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealLogViewHolder holder, int position) {
        MealLog mealLog = mealLogs.get(position);
        holder.tvMealFoodName.setText(mealLog.getFoodName());
        holder.tvMealType.setText("Tipo: " + mealLog.getMealType());
        holder.tvMealCalories.setText(mealLog.getCalories() + " kcal");
        holder.tvMealHealthyLevel.setText("Nivel: " + mealLog.getHealthyLevel());
    }

    @Override
    public int getItemCount() {
        return mealLogs.size();
    }

    public void setMealLogs(List<MealLog> mealLogs) {
        this.mealLogs = mealLogs;
        notifyDataSetChanged();
    }

    static class MealLogViewHolder extends RecyclerView.ViewHolder {
        TextView tvMealFoodName, tvMealType, tvMealCalories, tvMealHealthyLevel;

        public MealLogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMealFoodName = itemView.findViewById(R.id.tvMealFoodName);
            tvMealType = itemView.findViewById(R.id.tvMealType);
            tvMealCalories = itemView.findViewById(R.id.tvMealCalories);
            tvMealHealthyLevel = itemView.findViewById(R.id.tvMealHealthyLevel);
        }
    }
}