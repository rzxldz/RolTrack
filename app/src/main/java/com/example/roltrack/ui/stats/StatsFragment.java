package com.example.roltrack.ui.stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.roltrack.R;
import com.example.roltrack.viewmodel.StatsViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StatsFragment extends Fragment {

    private StatsViewModel statsViewModel;
    private BarChart barChartWater, barChartCalories, barChartSleep, barChartExercise;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        statsViewModel = new ViewModelProvider(this).get(StatsViewModel.class);

        barChartWater = view.findViewById(R.id.barChartWater);
        barChartCalories = view.findViewById(R.id.barChartCalories);
        barChartSleep = view.findViewById(R.id.barChartSleep);
        barChartExercise = view.findViewById(R.id.barChartExercise);

        loadWeeklyStats();
    }

    private void loadWeeklyStats() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            ArrayList<BarEntry> waterEntries = new ArrayList<>();
            ArrayList<BarEntry> caloriesEntries = new ArrayList<>();
            ArrayList<BarEntry> sleepEntries = new ArrayList<>();
            ArrayList<BarEntry> exerciseEntries = new ArrayList<>();

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            calendar.add(Calendar.DAY_OF_YEAR, -6);

            for (int i = 0; i < 7; i++) {
                String date = sdf.format(calendar.getTime());

                waterEntries.add(new BarEntry(i + 1, statsViewModel.getTotalWaterByDateSync(date)));
                caloriesEntries.add(new BarEntry(i + 1, statsViewModel.getTotalCaloriesByDateSync(date)));
                sleepEntries.add(new BarEntry(i + 1, (float) statsViewModel.getSleepHoursByDateSync(date)));
                exerciseEntries.add(new BarEntry(i + 1, statsViewModel.getExerciseMinutesByDateSync(date)));

                calendar.add(Calendar.DAY_OF_YEAR, 1);
            }

            requireActivity().runOnUiThread(() -> {
                setupChart(barChartWater, waterEntries, "Agua semanal (ml)");
                setupChart(barChartCalories, caloriesEntries, "Calorías semanales");
                setupChart(barChartSleep, sleepEntries, "Sueño semanal (h)");
                setupChart(barChartExercise, exerciseEntries, "Ejercicio semanal (min)");
            });
        });
    }

    private void setupChart(BarChart chart, ArrayList<BarEntry> entries, String label) {
        BarDataSet dataSet = new BarDataSet(entries, label);
        BarData data = new BarData(dataSet);
        chart.setData(data);
        chart.getDescription().setEnabled(false);
        chart.invalidate();
    }
}