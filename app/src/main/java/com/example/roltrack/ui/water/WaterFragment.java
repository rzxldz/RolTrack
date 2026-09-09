package com.example.roltrack.ui.water;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roltrack.R;
import com.example.roltrack.data.entity.WaterLog;
import com.example.roltrack.utils.DatePickerUtils;
import com.example.roltrack.utils.DateUtils;
import com.example.roltrack.viewmodel.WaterViewModel;

public class WaterFragment extends Fragment {

    private WaterViewModel waterViewModel;
    private ProgressBar progressBarWater;
    private TextView tvWaterTotal, tvSelectedWaterDate, tvWaterEmpty;
    private RecyclerView recyclerWaterLogs;
    private WaterLogAdapter adapter;

    private final int goalMl = 2000;
    private String selectedDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_water, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn250 = view.findViewById(R.id.btnAdd250);
        Button btn500 = view.findViewById(R.id.btnAdd500);
        Button btn1000 = view.findViewById(R.id.btnAdd1000);
        Button btnPickWaterDate = view.findViewById(R.id.btnPickWaterDate);

        progressBarWater = view.findViewById(R.id.progressBarWater);
        tvWaterTotal = view.findViewById(R.id.tvWaterTotal);
        tvSelectedWaterDate = view.findViewById(R.id.tvSelectedWaterDate);
        recyclerWaterLogs = view.findViewById(R.id.recyclerWaterLogs);

        waterViewModel = new ViewModelProvider(this).get(WaterViewModel.class);

        adapter = new WaterLogAdapter();
        recyclerWaterLogs.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerWaterLogs.setAdapter(adapter);

        selectedDate = DateUtils.getTodayDate();
        tvSelectedWaterDate.setText("Fecha: " + selectedDate);

        btn250.setOnClickListener(v -> addWater(250));
        btn500.setOnClickListener(v -> addWater(500));
        btn1000.setOnClickListener(v -> addWater(1000));

        btnPickWaterDate.setOnClickListener(v ->
                DatePickerUtils.showDatePicker(requireContext(), date -> {
                    selectedDate = date;
                    tvSelectedWaterDate.setText("Fecha: " + selectedDate);
                    observeWater();
                })
        );

        observeWater();

        android.view.animation.Animation animation =
                android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in);

        view.startAnimation(animation);
    }

    private void addWater(int amountMl) {
        WaterLog waterLog = new WaterLog(
                selectedDate,
                DateUtils.getCurrentTime(),
                amountMl
        );
        waterViewModel.insert(waterLog);
    }

    private void observeWater() {
        waterViewModel.getTotalWaterByDate(selectedDate).observe(getViewLifecycleOwner(), total -> {
            int current = total == null ? 0 : total;
            tvWaterTotal.setText(current + " / " + goalMl + " ml");
            progressBarWater.setMax(goalMl);
            progressBarWater.setProgress(Math.min(current, goalMl));
        });

        waterViewModel.getWaterLogsByDate(selectedDate).observe(getViewLifecycleOwner(), waterLogs -> {
            adapter.setWaterLogs(waterLogs);
        });
    }
}