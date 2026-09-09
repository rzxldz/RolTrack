package com.example.roltrack.data.repository;

import android.app.Application;
import android.location.Location;

import androidx.lifecycle.LiveData;

import com.example.roltrack.data.dao.RunPointDao;
import com.example.roltrack.data.dao.RunSessionDao;
import com.example.roltrack.data.db.AppDatabase;
import com.example.roltrack.data.entity.RunPoint;
import com.example.roltrack.data.entity.RunSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunRepository {

    private final RunSessionDao runSessionDao;
    private final RunPointDao runPointDao;
    private final ExecutorService executorService;

    public RunRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        runSessionDao = db.runSessionDao();
        runPointDao = db.runPointDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<RunSession>> getAllSessions() {
        return runSessionDao.getAllSessions();
    }

    public void insertRunWithPoints(RunSession session, List<Location> locations) {
        executorService.execute(() -> {
            long sessionId = runSessionDao.insert(session);

            List<RunPoint> points = new ArrayList<>();
            for (Location location : locations) {
                points.add(new RunPoint(
                        (int) sessionId,
                        location.getLatitude(),
                        location.getLongitude(),
                        location.getTime()
                ));
            }

            runPointDao.insertAll(points);
        });
    }

    public RunSession getLatestSessionSync() {
        return runSessionDao.getLatestSessionSync();
    }

    public List<RunPoint> getPointsForSession(int sessionId) {
        return runPointDao.getPointsForSession(sessionId);
    }
}