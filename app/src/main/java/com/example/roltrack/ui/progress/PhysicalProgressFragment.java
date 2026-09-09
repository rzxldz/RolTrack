package com.example.roltrack.ui.progress;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roltrack.R;
import com.example.roltrack.data.entity.UserProfile;
import com.example.roltrack.data.entity.WeightLog;
import com.example.roltrack.utils.DateUtils;
import com.example.roltrack.utils.PhysicalProgressUtils;
import com.example.roltrack.viewmodel.ProfileViewModel;
import com.example.roltrack.viewmodel.WeightLogViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PhysicalProgressFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private WeightLogViewModel weightLogViewModel;

    private TextView tvProgressName;
    private TextView tvProgressWeight;
    private TextView tvProgressHeight;
    private TextView tvProgressBMI;
    private TextView tvProgressCategory;
    private TextView tvProgressGoal;
    private TextView tvProgressFocus;
    private TextView tvProgressRecommendation;

    private EditText etNewWeight;
    private MaterialButton btnSaveWeight;
    private RecyclerView recyclerWeightLogs;
    private WeightLogAdapter adapter;
    private LineChart chartWeightProgress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_physical_progress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        weightLogViewModel = new ViewModelProvider(this).get(WeightLogViewModel.class);

        tvProgressName = view.findViewById(R.id.tvProgressName);
        tvProgressWeight = view.findViewById(R.id.tvProgressWeight);
        tvProgressHeight = view.findViewById(R.id.tvProgressHeight);
        tvProgressBMI = view.findViewById(R.id.tvProgressBMI);
        tvProgressCategory = view.findViewById(R.id.tvProgressCategory);
        tvProgressGoal = view.findViewById(R.id.tvProgressGoal);
        tvProgressFocus = view.findViewById(R.id.tvProgressFocus);
        tvProgressRecommendation = view.findViewById(R.id.tvProgressRecommendation);

        etNewWeight = view.findViewById(R.id.etNewWeight);
        btnSaveWeight = view.findViewById(R.id.btnSaveWeight);
        recyclerWeightLogs = view.findViewById(R.id.recyclerWeightLogs);
        chartWeightProgress = view.findViewById(R.id.chartWeightProgress);

        adapter = new WeightLogAdapter();
        recyclerWeightLogs.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerWeightLogs.setAdapter(adapter);
        recyclerWeightLogs.setNestedScrollingEnabled(false);

        profileViewModel.getUserProfile().observe(getViewLifecycleOwner(), this::showProgress);

        weightLogViewModel.getAllWeightLogs().observe(getViewLifecycleOwner(), logs -> {
            adapter.setLogs(logs);
            updateChart(logs);
        });

        btnSaveWeight.setOnClickListener(v -> saveWeight());
    }

    private void showProgress(UserProfile profile) {
        if (profile == null) {
            tvProgressName.setText("No hay perfil");
            tvProgressWeight.setText("");
            tvProgressHeight.setText("");
            tvProgressBMI.setText("");
            tvProgressCategory.setText("");
            tvProgressGoal.setText("");
            tvProgressFocus.setText("");
            tvProgressRecommendation.setText("Primero crea tu perfil para ver tu progreso físico.");
            return;
        }

        double bmi = PhysicalProgressUtils.calculateBMI(profile.getWeight(), profile.getHeight());
        String category = PhysicalProgressUtils.getBMICategory(bmi);
        String recommendation = PhysicalProgressUtils.getRecommendation(bmi, profile.getMainGoal());
        String focus = PhysicalProgressUtils.getSuggestedFocus(bmi, profile.getMainGoal());

        tvProgressName.setText(profile.getName());
        tvProgressWeight.setText("Peso actual: " + profile.getWeight() + " kg");
        tvProgressHeight.setText("Estatura: " + profile.getHeight() + " cm");
        tvProgressBMI.setText(String.format(Locale.getDefault(), "IMC: %.1f", bmi));
        tvProgressCategory.setText("Categoría: " + category);
        tvProgressGoal.setText("Objetivo actual: " + profile.getMainGoal());
        tvProgressFocus.setText(focus);
        tvProgressRecommendation.setText(recommendation);
    }

    private void saveWeight() {
        String weightText = etNewWeight.getText().toString().trim();

        if (TextUtils.isEmpty(weightText)) {
            Toast.makeText(requireContext(), "Ingresa un peso", Toast.LENGTH_SHORT).show();
            return;
        }

        double newWeight = Double.parseDouble(weightText);

        WeightLog weightLog = new WeightLog(
                DateUtils.getTodayDate(),
                newWeight
        );

        weightLogViewModel.insert(weightLog);
        profileViewModel.updateWeight(newWeight);

        etNewWeight.setText("");
        Toast.makeText(requireContext(), "Peso guardado", Toast.LENGTH_SHORT).show();
    }

    private void updateChart(List<WeightLog> logs) {
        List<Entry> entries = new ArrayList<>();

        for (int i = 0; i < logs.size(); i++) {
            entries.add(new Entry(i, (float) logs.get(i).getWeight()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Peso");
        LineData data = new LineData(dataSet);

        chartWeightProgress.setData(data);
        chartWeightProgress.getDescription().setEnabled(false);
        chartWeightProgress.invalidate();
    }
}