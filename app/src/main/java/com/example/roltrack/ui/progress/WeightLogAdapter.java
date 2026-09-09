package com.example.roltrack.ui.progress;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roltrack.R;
import com.example.roltrack.data.entity.WeightLog;

import java.util.ArrayList;
import java.util.List;

public class WeightLogAdapter extends RecyclerView.Adapter<WeightLogAdapter.WeightLogViewHolder> {

    private List<WeightLog> logs = new ArrayList<>();

    @NonNull
    @Override
    public WeightLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weight_log, parent, false);
        return new WeightLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeightLogViewHolder holder, int position) {
        WeightLog log = logs.get(position);
        holder.tvWeightLogDate.setText(log.getDate());
        holder.tvWeightLogValue.setText(log.getWeight() + " kg");
    }

    @Override
    public int getItemCount() {
        return logs == null ? 0 : logs.size();
    }

    public void setLogs(List<WeightLog> logs) {
        this.logs = logs == null ? new ArrayList<>() : logs;
        notifyDataSetChanged();
    }

    static class WeightLogViewHolder extends RecyclerView.ViewHolder {
        TextView tvWeightLogDate, tvWeightLogValue;

        public WeightLogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWeightLogDate = itemView.findViewById(R.id.tvWeightLogDate);
            tvWeightLogValue = itemView.findViewById(R.id.tvWeightLogValue);
        }
    }
}