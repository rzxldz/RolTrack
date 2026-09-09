package com.example.roltrack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roltrack.data.entity.UserProfile;
import com.example.roltrack.data.repository.UserRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileViewModel extends AndroidViewModel {

    private final UserRepository repository;
    private final ExecutorService executorService;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<UserProfile> getUserProfile() {
        return repository.getUserProfile();
    }

    public void insertUserProfile(UserProfile userProfile) {
        repository.insert(userProfile);
    }

    public void updateUserProfile(UserProfile userProfile) {
        repository.update(userProfile);
    }

    public interface ProfileCallback {
        void onLoaded(UserProfile userProfile);
    }

    public void loadUserProfileSync(ProfileCallback callback) {
        executorService.execute(() -> {
            UserProfile profile = repository.getUserProfileSync();
            callback.onLoaded(profile);
        });
    }

    public void updateWeight(double newWeight) {
        repository.updateWeight(newWeight);
    }
}