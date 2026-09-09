package com.example.roltrack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roltrack.data.entity.WaterLog;
import com.example.roltrack.data.repository.WaterRepository;

import java.util.List;

public class WaterViewModel extends AndroidViewModel {

    private final WaterRepository repository;

    public WaterViewModel(@NonNull Application application) {
        super(application);
        repository = new WaterRepository(application);
    }

    public void insert(WaterLog waterLog) {
        repository.insert(waterLog);
    }

    public LiveData<List<WaterLog>> getWaterLogsByDate(String date) {
        return repository.getWaterLogsByDate(date);
    }

    public LiveData<Integer> getTotalWaterByDate(String date) {
        return repository.getTotalWaterByDate(date);
    }
}