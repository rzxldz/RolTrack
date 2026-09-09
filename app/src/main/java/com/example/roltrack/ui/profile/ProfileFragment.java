package com.example.roltrack.ui.profile;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.roltrack.R;
import com.example.roltrack.data.entity.UserProfile;
import com.example.roltrack.ui.profile.EditProfileFragment;
import com.example.roltrack.ui.progress.PhysicalProgressFragment;
import com.example.roltrack.viewmodel.ProfileViewModel;
import com.google.android.material.button.MaterialButton;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    private ImageView ivProfilePhoto;
    private TextView tvProfileName;
    private TextView tvProfileGoal;
    private TextView tvProfileWeight;
    private TextView tvProfileHeight;
    private TextView tvProfileAge;
    private TextView tvProfileWaterGoal;
    private TextView tvProfileSleepGoal;
    private TextView tvProfileExerciseGoal;
    private MaterialButton btnEditProfile;
    private MaterialButton btnOpenPhysicalProgress;

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        ivProfilePhoto = view.findViewById(R.id.ivProfilePhoto);
        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileGoal = view.findViewById(R.id.tvProfileGoal);
        tvProfileWeight = view.findViewById(R.id.tvProfileWeight);
        tvProfileHeight = view.findViewById(R.id.tvProfileHeight);
        tvProfileAge = view.findViewById(R.id.tvProfileAge);
        tvProfileWaterGoal = view.findViewById(R.id.tvProfileWaterGoal);
        tvProfileSleepGoal = view.findViewById(R.id.tvProfileSleepGoal);
        tvProfileExerciseGoal = view.findViewById(R.id.tvProfileExerciseGoal);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnOpenPhysicalProgress = view.findViewById(R.id.btnOpenPhysicalProgress);

        profileViewModel.getUserProfile().observe(getViewLifecycleOwner(), this::showProfile);

        btnEditProfile.setOnClickListener(v -> openFragment(new EditProfileFragment()));
        btnOpenPhysicalProgress.setOnClickListener(v -> openFragment(new PhysicalProgressFragment()));
    }

    private void showProfile(UserProfile profile) {
        if (profile == null) {
            tvProfileName.setText("Sin perfil");
            tvProfileGoal.setText("Crea tu perfil");
            tvProfileWeight.setText("--");
            tvProfileHeight.setText("--");
            tvProfileAge.setText("--");
            tvProfileWaterGoal.setText("Agua: --");
            tvProfileSleepGoal.setText("Sueño: --");
            tvProfileExerciseGoal.setText("Ejercicio semanal: --");
            ivProfilePhoto.setImageResource(R.mipmap.ic_launcher);
            return;
        }

        tvProfileName.setText(profile.getName());
        tvProfileGoal.setText(profile.getMainGoal());
        tvProfileWeight.setText(profile.getWeight() + " kg");
        tvProfileHeight.setText(profile.getHeight() + " cm");
        tvProfileAge.setText(String.valueOf(profile.getAge()));
        tvProfileWaterGoal.setText("Agua: " + profile.getDailyWaterGoalMl() + " ml");
        tvProfileSleepGoal.setText("Sueño: " + profile.getDailySleepGoalHours() + " h");
        tvProfileExerciseGoal.setText("Ejercicio semanal: " + profile.getWeeklyExerciseGoalMinutes() + " min");

        String photoUri = profile.getPhotoUri();
        if (photoUri != null && !photoUri.isEmpty()) {
            try {
                ivProfilePhoto.setImageURI(Uri.parse(photoUri));
            } catch (Exception e) {
                ivProfilePhoto.setImageResource(R.mipmap.ic_launcher);
            }
        } else {
            ivProfilePhoto.setImageResource(R.mipmap.ic_launcher);
        }
    }

    private void openFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}