package com.example.roltrack.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roltrack.data.dao.ExerciseLogDao;
import com.example.roltrack.data.dao.GoalDao;
import com.example.roltrack.data.dao.MealLogDao;
import com.example.roltrack.data.dao.ReminderDao;
import com.example.roltrack.data.dao.RunPointDao;
import com.example.roltrack.data.dao.RunSessionDao;
import com.example.roltrack.data.dao.SavedMealPlanDao;
import com.example.roltrack.data.dao.SavedWorkoutDao;
import com.example.roltrack.data.dao.SleepLogDao;
import com.example.roltrack.data.dao.UserProfileDao;
import com.example.roltrack.data.dao.WaterLogDao;
import com.example.roltrack.data.dao.WeightLogDao;
import com.example.roltrack.data.entity.ExerciseLog;
import com.example.roltrack.data.entity.Goal;
import com.example.roltrack.data.entity.MealLog;
import com.example.roltrack.data.entity.Reminder;
import com.example.roltrack.data.entity.RunPoint;
import com.example.roltrack.data.entity.RunSession;
import com.example.roltrack.data.entity.SavedMealPlan;
import com.example.roltrack.data.entity.SavedWorkout;
import com.example.roltrack.data.entity.SleepLog;
import com.example.roltrack.data.entity.UserProfile;
import com.example.roltrack.data.entity.WaterLog;
import com.example.roltrack.data.entity.WeightLog;

@Database(
        entities = {
                UserProfile.class,
                WaterLog.class,
                MealLog.class,
                SleepLog.class,
                ExerciseLog.class,
                Goal.class,
                Reminder.class,
                SavedWorkout.class,
                SavedMealPlan.class,
                WeightLog.class,
                RunSession.class,
                RunPoint.class
        },
        version = 18,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract UserProfileDao userProfileDao();
    public abstract WaterLogDao waterLogDao();
    public abstract MealLogDao mealLogDao();
    public abstract SleepLogDao sleepLogDao();
    public abstract ExerciseLogDao exerciseLogDao();
    public abstract GoalDao goalDao();
    public abstract ReminderDao reminderDao();
    public abstract SavedWorkoutDao savedWorkoutDao();
    public abstract SavedMealPlanDao savedMealPlanDao();
    public abstract WeightLogDao weightLogDao();
    public abstract RunSessionDao runSessionDao();
    public abstract RunPointDao runPointDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "roltrack_database"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}