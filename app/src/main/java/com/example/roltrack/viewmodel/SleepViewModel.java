package com.example.roltrack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roltrack.data.entity.SleepLog;
import com.example.roltrack.data.repository.SleepRepository;

import java.util.List;

public class SleepViewModel extends AndroidViewModel {

    private final SleepRepository repository;

    public SleepViewModel(@NonNull Application application) {
        super(application);
        repository = new SleepRepository(application);
    }

    public void insert(SleepLog sleepLog) {
        repository.insert(sleepLog);
    }

    public LiveData<List<SleepLog>> getSleepLogsByDate(String date) {
        return repository.getSleepLogsByDate(date);
    }

    public LiveData<Double> getSleepHoursByDate(String date) {
        return repository.getSleepHoursByDate(date);
    }
}