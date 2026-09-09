package com.example.roltrack.viewmodel;

import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roltrack.data.entity.RunSession;
import com.example.roltrack.data.repository.RunRepository;

import java.util.List;

public class RunViewModel extends AndroidViewModel {

    private final RunRepository repository;

    public RunViewModel(@NonNull Application application) {
        super(application);
        repository = new RunRepository(application);
    }

    public LiveData<List<RunSession>> getAllSessions() {
        return repository.getAllSessions();
    }

    public void insertRunWithPoints(RunSession session, List<Location> locations) {
        repository.insertRunWithPoints(session, locations);
    }

    public RunSession getLatestSessionSync() {
        return repository.getLatestSessionSync();
    }

    public List<com.example.roltrack.data.entity.RunPoint> getPointsForSession(int sessionId) {
        return repository.getPointsForSession(sessionId);
    }
}