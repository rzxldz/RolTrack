package com.example.roltrack.ui.workout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roltrack.R;
import com.example.roltrack.workout.WorkoutDay;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDayAdapter extends RecyclerView.Adapter<WorkoutDayAdapter.WorkoutDayViewHolder> {

    private List<WorkoutDay> days = new ArrayList<>();

    @NonNull
    @Override
    public WorkoutDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_workout_day, parent, false);
        return new WorkoutDayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutDayViewHolder holder, int position) {
        WorkoutDay day = days.get(position);
        holder.tvWorkoutDayTitle.setText(day.getDayTitle());
        holder.tvWorkoutDayExercises.setText(day.getExercises());
    }

    @Override
    public int getItemCount() {
        return days == null ? 0 : days.size();
    }

    public void setDays(List<WorkoutDay> days) {
        this.days = days == null ? new ArrayList<>() : days;
        notifyDataSetChanged();
    }

    static class WorkoutDayViewHolder extends RecyclerView.ViewHolder {
        TextView tvWorkoutDayTitle;
        TextView tvWorkoutDayExercises;

        public WorkoutDayViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWorkoutDayTitle = itemView.findViewById(R.id.tvWorkoutDayTitle);
            tvWorkoutDayExercises = itemView.findViewById(R.id.tvWorkoutDayExercises);
        }
    }
}