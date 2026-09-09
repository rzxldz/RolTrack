package com.example.roltrack.ui.exercise;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roltrack.R;
import com.example.roltrack.data.entity.ExerciseLog;

import java.util.ArrayList;
import java.util.List;

public class ExerciseLogAdapter extends RecyclerView.Adapter<ExerciseLogAdapter.ExerciseLogViewHolder> {

    private List<ExerciseLog> exerciseLogs = new ArrayList<>();

    @NonNull
    @Override
    public ExerciseLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exercise_log, parent, false);
        return new ExerciseLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseLogViewHolder holder, int position) {
        ExerciseLog exerciseLog = exerciseLogs.get(position);
        holder.tvExerciseTypeItem.setText(exerciseLog.getExerciseType());
        holder.tvExerciseDurationItem.setText(exerciseLog.getDurationMinutes() + " min");
        holder.tvExerciseIntensityItem.setText("Intensidad: " + exerciseLog.getIntensity());
        holder.tvExerciseCaloriesItem.setText(exerciseLog.getEstimatedCalories() + " kcal");
    }

    @Override
    public int getItemCount() {
        return exerciseLogs.size();
    }

    public void setExerciseLogs(List<ExerciseLog> exerciseLogs) {
        this.exerciseLogs = exerciseLogs;
        notifyDataSetChanged();
    }

    static class ExerciseLogViewHolder extends RecyclerView.ViewHolder {
        TextView tvExerciseTypeItem, tvExerciseDurationItem, tvExerciseIntensityItem, tvExerciseCaloriesItem;

        public ExerciseLogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExerciseTypeItem = itemView.findViewById(R.id.tvExerciseTypeItem);
            tvExerciseDurationItem = itemView.findViewById(R.id.tvExerciseDurationItem);
            tvExerciseIntensityItem = itemView.findViewById(R.id.tvExerciseIntensityItem);
            tvExerciseCaloriesItem = itemView.findViewById(R.id.tvExerciseCaloriesItem);
        }
    }
}