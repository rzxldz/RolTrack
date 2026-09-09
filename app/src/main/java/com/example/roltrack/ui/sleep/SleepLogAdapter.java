package com.example.roltrack.ui.sleep;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roltrack.R;
import com.example.roltrack.data.entity.SleepLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SleepLogAdapter extends RecyclerView.Adapter<SleepLogAdapter.SleepLogViewHolder> {

    private List<SleepLog> sleepLogs = new ArrayList<>();

    @NonNull
    @Override
    public SleepLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sleep_log, parent, false);
        return new SleepLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SleepLogViewHolder holder, int position) {
        SleepLog sleepLog = sleepLogs.get(position);
        holder.tvSleepHoursItem.setText(String.format(Locale.getDefault(), "%.1f h", sleepLog.getHoursSlept()));
        holder.tvSleepRangeItem.setText(sleepLog.getSleepStart() + " - " + sleepLog.getSleepEnd());
        holder.tvSleepQualityItem.setText("Calidad: " + sleepLog.getSleepQuality() + "/5");
    }

    @Override
    public int getItemCount() {
        return sleepLogs.size();
    }

    public void setSleepLogs(List<SleepLog> sleepLogs) {
        this.sleepLogs = sleepLogs;
        notifyDataSetChanged();
    }

    static class SleepLogViewHolder extends RecyclerView.ViewHolder {
        TextView tvSleepHoursItem, tvSleepRangeItem, tvSleepQualityItem;

        public SleepLogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSleepHoursItem = itemView.findViewById(R.id.tvSleepHoursItem);
            tvSleepRangeItem = itemView.findViewById(R.id.tvSleepRangeItem);
            tvSleepQualityItem = itemView.findViewById(R.id.tvSleepQualityItem);
        }
    }
}