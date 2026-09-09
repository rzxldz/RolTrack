package com.example.roltrack.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roltrack.data.entity.UserProfile;

@Dao
public interface UserProfileDao {

    @Insert
    void insert(UserProfile userProfile);

    @Update
    void update(UserProfile userProfile);

    @Query("SELECT * FROM user_profile LIMIT 1")
    LiveData<UserProfile> getUserProfile();

    @Query("SELECT * FROM user_profile LIMIT 1")
    UserProfile getUserProfileSync();

    @Query("UPDATE user_profile SET weight = :newWeight WHERE id = 1")
    void updateWeight(double newWeight);
}