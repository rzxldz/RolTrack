package com.example.roltrack.ui.onboarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roltrack.MainActivity;
import com.example.roltrack.R;
import com.example.roltrack.data.db.AppDatabase;
import com.example.roltrack.data.entity.UserProfile;

import java.util.concurrent.Executors;

public class OnboardingActivity extends AppCompatActivity {

    private EditText etName, etAge, etWeight, etHeight, etWaterGoal, etSleepGoal, etExerciseGoal;
    private AutoCompleteTextView actvGoal;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        etWaterGoal = findViewById(R.id.etWaterGoal);
        etSleepGoal = findViewById(R.id.etSleepGoal);
        etExerciseGoal = findViewById(R.id.etExerciseGoal);
        actvGoal = findViewById(R.id.actvGoal);
        btnSave = findViewById(R.id.btnSaveProfile);

        String[] goals = {"Bajar de peso", "Ganar energía", "Dormir mejor", "Mejorar hábitos"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, goals);
        actvGoal.setAdapter(adapter);

        btnSave.setOnClickListener(v -> saveProfile());
    }

    private void saveProfile() {
        String name = etName.getText().toString().trim();
        String ageText = etAge.getText().toString().trim();
        String weightText = etWeight.getText().toString().trim();
        String heightText = etHeight.getText().toString().trim();
        String mainGoal = actvGoal.getText().toString().trim();
        String waterGoalText = etWaterGoal.getText().toString().trim();
        String sleepGoalText = etSleepGoal.getText().toString().trim();
        String exerciseGoalText = etExerciseGoal.getText().toString().trim();

        if (name.isEmpty() || ageText.isEmpty() || weightText.isEmpty() || heightText.isEmpty()
                || mainGoal.isEmpty() || waterGoalText.isEmpty()
                || sleepGoalText.isEmpty() || exerciseGoalText.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        UserProfile userProfile = new UserProfile(
                name,
                Integer.parseInt(ageText),
                Double.parseDouble(weightText),
                Double.parseDouble(heightText),
                mainGoal,
                Integer.parseInt(waterGoalText),
                Integer.parseInt(sleepGoalText),
                Integer.parseInt(exerciseGoalText),
                ""
        );

        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase.getDatabase(getApplicationContext()).userProfileDao().insert(userProfile);

            runOnUiThread(() -> {
                SharedPreferences prefs = getSharedPreferences("roltrack_prefs", MODE_PRIVATE);
                prefs.edit().putBoolean("isFirstLaunch", false).apply();

                startActivity(new Intent(OnboardingActivity.this, MainActivity.class));
                finish();
            });
        });
    }
}