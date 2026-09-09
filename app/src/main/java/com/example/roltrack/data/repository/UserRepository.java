package com.example.roltrack.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.roltrack.data.dao.UserProfileDao;
import com.example.roltrack.data.db.AppDatabase;
import com.example.roltrack.data.entity.UserProfile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {

    private final UserProfileDao userProfileDao;
    private final ExecutorService executorService;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userProfileDao = db.userProfileDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(UserProfile userProfile) {
        executorService.execute(() -> userProfileDao.insert(userProfile));
    }

    public void update(UserProfile userProfile) {
        executorService.execute(() -> userProfileDao.update(userProfile));
    }

    public LiveData<UserProfile> getUserProfile() {
        return userProfileDao.getUserProfile();
    }

    public UserProfile getUserProfileSync() {
        return userProfileDao.getUserProfileSync();
    }

    public void updateWeight(double newWeight) {
        executorService.execute(() -> userProfileDao.updateWeight(newWeight));
    }
}