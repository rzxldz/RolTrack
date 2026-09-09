package com.example.roltrack.ui.profile;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.roltrack.R;
import com.example.roltrack.data.entity.UserProfile;
import com.example.roltrack.viewmodel.ProfileViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EditProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    private ImageView ivEditProfilePhoto;
    private EditText etProfileName;
    private EditText etProfileAge;
    private EditText etProfileWeight;
    private EditText etProfileHeight;
    private EditText etProfileGoal;
    private EditText etProfileWaterGoal;
    private EditText etProfileSleepGoal;
    private EditText etProfileExerciseGoal;

    private String selectedPhotoUri = "";
    private UserProfile currentProfile;

    private final ActivityResultLauncher<String[]> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.OpenDocument(), uri -> {
                if (uri != null) {
                    final int takeFlags = android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
                    requireContext().getContentResolver().takePersistableUriPermission(uri, takeFlags);

                    selectedPhotoUri = uri.toString();
                    ivEditProfilePhoto.setImageURI(uri);
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        ivEditProfilePhoto = view.findViewById(R.id.ivEditProfilePhoto);
        etProfileName = view.findViewById(R.id.etProfileName);
        etProfileAge = view.findViewById(R.id.etProfileAge);
        etProfileWeight = view.findViewById(R.id.etProfileWeight);
        etProfileHeight = view.findViewById(R.id.etProfileHeight);
        etProfileGoal = view.findViewById(R.id.etProfileGoal);
        etProfileWaterGoal = view.findViewById(R.id.etProfileWaterGoal);
        etProfileSleepGoal = view.findViewById(R.id.etProfileSleepGoal);
        etProfileExerciseGoal = view.findViewById(R.id.etProfileExerciseGoal);

        Button btnPickPhoto = view.findViewById(R.id.btnPickPhoto);
        Button btnSaveProfileChanges = view.findViewById(R.id.btnSaveProfileChanges);

        profileViewModel.getUserProfile().observe(getViewLifecycleOwner(), this::fillProfile);

        btnPickPhoto.setOnClickListener(v -> imagePickerLauncher.launch(new String[]{"image/*"}));
        btnSaveProfileChanges.setOnClickListener(v -> saveProfileChanges());
    }

    private void fillProfile(UserProfile userProfile) {
        currentProfile = userProfile;

        if (userProfile == null) return;

        etProfileName.setText(userProfile.getName());
        etProfileAge.setText(String.valueOf(userProfile.getAge()));
        etProfileWeight.setText(String.valueOf(userProfile.getWeight()));
        etProfileHeight.setText(String.valueOf(userProfile.getHeight()));
        etProfileGoal.setText(userProfile.getMainGoal());
        etProfileWaterGoal.setText(String.valueOf(userProfile.getDailyWaterGoalMl()));
        etProfileSleepGoal.setText(String.valueOf(userProfile.getDailySleepGoalHours()));
        etProfileExerciseGoal.setText(String.valueOf(userProfile.getWeeklyExerciseGoalMinutes()));

        selectedPhotoUri = userProfile.getPhotoUri() == null ? "" : userProfile.getPhotoUri();
        if (!selectedPhotoUri.isEmpty()) {
            ivEditProfilePhoto.setImageURI(Uri.parse(selectedPhotoUri));
        }
    }

    private void saveProfileChanges() {
        String name = etProfileName.getText().toString().trim();
        String ageText = etProfileAge.getText().toString().trim();
        String weightText = etProfileWeight.getText().toString().trim();
        String heightText = etProfileHeight.getText().toString().trim();
        String goal = etProfileGoal.getText().toString().trim();
        String waterGoalText = etProfileWaterGoal.getText().toString().trim();
        String sleepGoalText = etProfileSleepGoal.getText().toString().trim();
        String exerciseGoalText = etProfileExerciseGoal.getText().toString().trim();

        if (name.isEmpty() || ageText.isEmpty() || weightText.isEmpty() || heightText.isEmpty()
                || goal.isEmpty() || waterGoalText.isEmpty() || sleepGoalText.isEmpty() || exerciseGoalText.isEmpty()) {
            Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentProfile == null) {
            UserProfile newProfile = new UserProfile(
                    name,
                    Integer.parseInt(ageText),
                    Double.parseDouble(weightText),
                    Double.parseDouble(heightText),
                    goal,
                    Integer.parseInt(waterGoalText),
                    Integer.parseInt(sleepGoalText),
                    Integer.parseInt(exerciseGoalText),
                    selectedPhotoUri
            );

            profileViewModel.insertUserProfile(newProfile);
            Toast.makeText(requireContext(), "Perfil creado", Toast.LENGTH_SHORT).show();
        } else {
            currentProfile.setName(name);
            currentProfile.setAge(Integer.parseInt(ageText));
            currentProfile.setWeight(Double.parseDouble(weightText));
            currentProfile.setHeight(Double.parseDouble(heightText));
            currentProfile.setMainGoal(goal);
            currentProfile.setDailyWaterGoalMl(Integer.parseInt(waterGoalText));
            currentProfile.setDailySleepGoalHours(Integer.parseInt(sleepGoalText));
            currentProfile.setWeeklyExerciseGoalMinutes(Integer.parseInt(exerciseGoalText));
            currentProfile.setPhotoUri(selectedPhotoUri);

            profileViewModel.updateUserProfile(currentProfile);
            Toast.makeText(requireContext(), "Perfil actualizado", Toast.LENGTH_SHORT).show();
        }

        requireActivity().getSupportFragmentManager().popBackStack();
    }
}