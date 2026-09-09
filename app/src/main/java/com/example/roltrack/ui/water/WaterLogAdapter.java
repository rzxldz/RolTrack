package com.example.roltrack.ui.water;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roltrack.R;
import com.example.roltrack.data.entity.WaterLog;

import java.util.ArrayList;
import java.util.List;

public class WaterLogAdapter extends RecyclerView.Adapter<WaterLogAdapter.WaterLogViewHolder> {

    private List<WaterLog> waterLogs = new ArrayList<>();

    @NonNull
    @Override
    public WaterLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_water_log, parent, false);
        return new WaterLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterLogViewHolder holder, int position) {
        WaterLog waterLog = waterLogs.get(position);
        holder.tvWaterAmount.setText(waterLog.getAmountMl() + " ml");
        holder.tvWaterTime.setText(waterLog.getTime());
    }

    @Override
    public int getItemCount() {
        return waterLogs == null ? 0 : waterLogs.size();
    }

    public void setWaterLogs(List<WaterLog> waterLogs) {
        this.waterLogs = waterLogs == null ? new ArrayList<>() : waterLogs;
        notifyDataSetChanged();
    }

    static class WaterLogViewHolder extends RecyclerView.ViewHolder {
        TextView tvWaterAmount;
        TextView tvWaterTime;

        public WaterLogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWaterAmount = itemView.findViewById(R.id.tvWaterLogAmount);
            tvWaterTime = itemView.findViewById(R.id.tvWaterLogTime);
        }
    }
}