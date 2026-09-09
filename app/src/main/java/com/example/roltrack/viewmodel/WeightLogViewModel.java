package com.example.roltrack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roltrack.data.entity.WeightLog;
import com.example.roltrack.data.repository.WeightLogRepository;

import java.util.List;

public class WeightLogViewModel extends AndroidViewModel {

    private final WeightLogRepository repository;

    public WeightLogViewModel(@NonNull Application application) {
        super(application);
        repository = new WeightLogRepository(application);
    }

    public void insert(WeightLog weightLog) {
        repository.insert(weightLog);
    }

    public LiveData<List<WeightLog>> getAllWeightLogs() {
        return repository.getAllWeightLogs();
    }

    public LiveData<WeightLog> getLatestWeightLog() {
        return repository.getLatestWeightLog();
    }
}