package com.example.roltrack.running;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.roltrack.R;
import com.example.roltrack.data.entity.RunSession;
import com.example.roltrack.utils.DateUtils;
import com.example.roltrack.viewmodel.RunViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;


public class RunTrackingFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private TextView tvRunTime, tvRunDistance, tvRunPace;
    private MaterialButton btnStartRun, btnFinishRun;
    private RunViewModel runViewModel;
    private com.google.android.gms.maps.model.Marker startMarker;
    private com.google.android.gms.maps.model.Marker endMarker;
    private boolean hasCenteredInitially = false;

    public RunTrackingFragment() {
        super(R.layout.fragment_run_tracking);
    }

    private final ActivityResultLauncher<String[]> permissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);

                if (Boolean.TRUE.equals(fineLocationGranted)) {
                    Intent intent = new Intent(requireContext(), RunTrackingService.class);
                    ContextCompat.startForegroundService(requireContext(), intent);
                } else {
                    Toast.makeText(requireContext(), "Se necesita permiso de ubicación", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        runViewModel = new ViewModelProvider(this).get(RunViewModel.class);

        tvRunTime = view.findViewById(R.id.tvRunTime);
        tvRunDistance = view.findViewById(R.id.tvRunDistance);
        tvRunPace = view.findViewById(R.id.tvRunPace);
        btnStartRun = view.findViewById(R.id.btnStartRun);
        btnFinishRun = view.findViewById(R.id.btnFinishRun);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.runMap);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        btnStartRun.setOnClickListener(v -> startRun());
        btnFinishRun.setOnClickListener(v -> finishRun());

        updateTrackingUi(false);

        RunTrackingService.isTracking.observe(getViewLifecycleOwner(), tracking -> {
            boolean isRunning = tracking != null && tracking;
            updateTrackingUi(isRunning);
        });

        RunTrackingService.elapsedTimeMillis.observe(getViewLifecycleOwner(), millis -> {
            long value = millis == null ? 0L : millis;
            tvRunTime.setText(RunStatsUtils.formatDuration(value));

            float distance = RunTrackingService.distanceMeters.getValue() == null
                    ? 0f
                    : RunTrackingService.distanceMeters.getValue();

            if (distance < 100f) {
                tvRunPace.setText("--:-- /km");
            } else {
                float pace = RunStatsUtils.calculatePaceSecPerKm(value, distance);
                tvRunPace.setText(RunStatsUtils.formatPace(pace));
            }
        });

        RunTrackingService.distanceMeters.observe(getViewLifecycleOwner(), meters -> {
            float value = meters == null ? 0f : meters;
            tvRunDistance.setText(RunStatsUtils.formatDistanceKm(value));
        });

        RunTrackingService.pathPoints.observe(getViewLifecycleOwner(), this::drawPath);
    }

    private void updateTrackingUi(boolean tracking) {
        if (tracking) {
            btnStartRun.setText("Corriendo...");
            btnStartRun.setEnabled(false);
            btnFinishRun.setEnabled(true);
        } else {
            btnStartRun.setText("Iniciar carrera");
            btnStartRun.setEnabled(true);
            btnFinishRun.setEnabled(false);
        }
    }

    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissionsIfNeeded() {
        List<String> perms = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            perms.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (Build.VERSION.SDK_INT >= 33 &&
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED) {
            perms.add(Manifest.permission.POST_NOTIFICATIONS);
        }

        if (!perms.isEmpty()) {
            permissionLauncher.launch(perms.toArray(new String[0]));
        }
    }

    private void startRun() {
        Boolean tracking = RunTrackingService.isTracking.getValue();
        if (Boolean.TRUE.equals(tracking)) {
            Toast.makeText(requireContext(), "Ya hay una carrera en curso", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!hasLocationPermission()) {
            requestPermissionsIfNeeded();
            return;
        }

        Intent intent = new Intent(requireContext(), RunTrackingService.class);
        ContextCompat.startForegroundService(requireContext(), intent);

        try {
            if (googleMap != null) {
                googleMap.setMyLocationEnabled(true);
            }
        } catch (SecurityException ignored) {
        }

        moveCameraToCurrentLocation();

        Toast.makeText(requireContext(), "Tracking iniciado", Toast.LENGTH_SHORT).show();
    }

    private void finishRun() {
        long duration = RunTrackingService.elapsedTimeMillis.getValue() == null
                ? 0L
                : RunTrackingService.elapsedTimeMillis.getValue();

        float distance = RunTrackingService.distanceMeters.getValue() == null
                ? 0f
                : RunTrackingService.distanceMeters.getValue();

        if (duration <= 0L || distance <= 0f) {
            requireContext().stopService(new Intent(requireContext(), RunTrackingService.class));
            Toast.makeText(requireContext(), "No hay datos suficientes para guardar la carrera", Toast.LENGTH_SHORT).show();
            updateTrackingUi(false);
            return;
        }

        List<Location> locations = RunTrackingService.pathPoints.getValue() == null
                ? new ArrayList<>()
                : new ArrayList<>(RunTrackingService.pathPoints.getValue());

        float pace = RunStatsUtils.calculatePaceSecPerKm(duration, distance);

        RunSession session = new RunSession(
                DateUtils.getTodayDate(),
                duration,
                distance,
                pace,
                ""
        );

        runViewModel.insertRunWithPoints(session, locations);

        requireContext().stopService(new Intent(requireContext(), RunTrackingService.class));

        if (googleMap != null) {
            googleMap.clear();
        }

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, RunSummaryFragment.newInstance(distance, duration, pace))
                .addToBackStack(null)
                .commit();
    }

    private void drawPath(List<Location> locations) {
        if (googleMap == null || locations == null || locations.isEmpty()) return;

        googleMap.clear();

        PolylineOptions polylineOptions = new PolylineOptions()
                .width(12f)
                .color(android.graphics.Color.parseColor("#9BFF3F"));

        for (Location loc : locations) {
            polylineOptions.add(new LatLng(loc.getLatitude(), loc.getLongitude()));
        }

        googleMap.addPolyline(polylineOptions);

        Location last = locations.get(locations.size() - 1);
        LatLng lastLatLng = new LatLng(last.getLatitude(), last.getLongitude());

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng, 17f));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;

        try {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.getUiSettings().setZoomControlsEnabled(false);
            googleMap.getUiSettings().setMapToolbarEnabled(false);
            googleMap.getUiSettings().setCompassEnabled(true);

            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                googleMap.setMyLocationEnabled(true);
                moveCameraToCurrentLocation();
            }
        } catch (Exception ignored) {
        }
    }

    private void moveCameraToCurrentLocation() {
        if (googleMap == null) return;

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        com.google.android.gms.location.FusedLocationProviderClient fusedLocationClient =
                com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(requireContext());

        try {
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 17f));
                }
            });
        } catch (Exception ignored) {
        }
    }
}