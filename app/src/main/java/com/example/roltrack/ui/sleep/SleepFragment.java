package com.example.roltrack.ui.sleep;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roltrack.R;
import com.example.roltrack.data.entity.SleepLog;
import com.example.roltrack.utils.DatePickerUtils;
import com.example.roltrack.utils.DateUtils;
import com.example.roltrack.viewmodel.SleepViewModel;

import java.util.Locale;

public class SleepFragment extends Fragment {

    private SleepViewModel sleepViewModel;
    private EditText etSleepStart, etSleepEnd;
    private RatingBar ratingSleepQuality;
    private TextView tvSleepHours, tvSelectedSleepDate, tvSleepEmpty;
    private RecyclerView recyclerSleepLogs;
    private SleepLogAdapter adapter;
    private String selectedDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sleepViewModel = new ViewModelProvider(this).get(SleepViewModel.class);

        etSleepStart = view.findViewById(R.id.etSleepStart);
        etSleepEnd = view.findViewById(R.id.etSleepEnd);
        ratingSleepQuality = view.findViewById(R.id.ratingSleepQuality);

        tvSleepHours = view.findViewById(R.id.tvSleepHours);
        tvSelectedSleepDate = view.findViewById(R.id.tvSelectedSleepDate);
        tvSleepEmpty = view.findViewById(R.id.tvSleepEmpty);

        recyclerSleepLogs = view.findViewById(R.id.recyclerSleepLogs);
        Button btnSaveSleep = view.findViewById(R.id.btnSaveSleep);
        Button btnPickSleepDate = view.findViewById(R.id.btnPickSleepDate);

        adapter = new SleepLogAdapter();
        recyclerSleepLogs.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerSleepLogs.setAdapter(adapter);

        selectedDate = DateUtils.getTodayDate();
        tvSelectedSleepDate.setText("Fecha: " + selectedDate);

        btnSaveSleep.setOnClickListener(v -> saveSleep());

        btnPickSleepDate.setOnClickListener(v ->
                DatePickerUtils.showDatePicker(requireContext(), date -> {
                    selectedDate = date;
                    tvSelectedSleepDate.setText("Fecha: " + selectedDate);
                    observeSleep();
                })
        );

        observeSleep();
    }

    private void observeSleep() {
        sleepViewModel.getSleepLogsByDate(selectedDate).observe(getViewLifecycleOwner(), sleepLogs -> {
            adapter.setSleepLogs(sleepLogs);

            if (sleepLogs == null || sleepLogs.isEmpty()) {
                tvSleepEmpty.setVisibility(View.VISIBLE);
                recyclerSleepLogs.setVisibility(View.GONE);
            } else {
                tvSleepEmpty.setVisibility(View.GONE);
                recyclerSleepLogs.setVisibility(View.VISIBLE);
            }
        });
    }

    private void saveSleep() {
        String start = etSleepStart.getText().toString().trim();
        String end = etSleepEnd.getText().toString().trim();
        int quality = (int) ratingSleepQuality.getRating();

        if (start.isEmpty() || end.isEmpty() || quality == 0) {
            Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double hours = calculateSleepHours(start, end);

        SleepLog sleepLog = new SleepLog(
                selectedDate,
                start,
                end,
                hours,
                quality
        );

        sleepViewModel.insert(sleepLog);

        etSleepStart.setText("");
        etSleepEnd.setText("");
        ratingSleepQuality.setRating(0);

        Toast.makeText(requireContext(), "Sueño guardado", Toast.LENGTH_SHORT).show();

        tvSleepHours.setText(String.format(Locale.getDefault(), "%.1f hrs", hours));
    }

    private double calculateSleepHours(String start, String end) {
        try {
            String[] startParts = start.split(":");
            String[] endParts = end.split(":");

            int startHour = Integer.parseInt(startParts[0]);
            int startMinute = Integer.parseInt(startParts[1]);

            int endHour = Integer.parseInt(endParts[0]);
            int endMinute = Integer.parseInt(endParts[1]);

            int startTotalMinutes = startHour * 60 + startMinute;
            int endTotalMinutes = endHour * 60 + endMinute;

            // Si cruza medianoche
            if (endTotalMinutes < startTotalMinutes) {
                endTotalMinutes += 24 * 60;
            }

            int diffMinutes = endTotalMinutes - startTotalMinutes;

            return diffMinutes / 60.0;
        } catch (Exception e) {
            return 0.0;
        }
    }
}