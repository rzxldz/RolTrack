package com.example.roltrack;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.roltrack.ui.dashboard.DashboardFragment;
import com.example.roltrack.ui.habits.HabitsFragment;
import com.example.roltrack.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottomNavigation);

        if (savedInstanceState == null) {
            openFragment(new DashboardFragment());
            bottomNavigation.setSelectedItemId(R.id.nav_dashboard);
        }

        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_dashboard) {
                openFragment(new DashboardFragment());
                return true;
            } else if (id == R.id.nav_habits) {
                openFragment(new HabitsFragment());
                return true;
            } else if (id == R.id.nav_profile) {
                openFragment(new ProfileFragment());
                return true;
            }

            return false;
        });
    }

    private void openFragment(@NonNull Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}