package com.example.roltrack.running;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.roltrack.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import java.util.ArrayList;
import java.util.List;

public class RunTrackingService extends Service {

    public static final MutableLiveData<Boolean> isTracking = new MutableLiveData<>(false);
    public static final MutableLiveData<Long> elapsedTimeMillis = new MutableLiveData<>(0L);
    public static final MutableLiveData<Float> distanceMeters = new MutableLiveData<>(0f);
    public static final MutableLiveData<List<Location>> pathPoints = new MutableLiveData<>(new ArrayList<>());

    private static final String CHANNEL_ID = "run_tracking_channel";
    private static final int NOTIFICATION_ID = 2001;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    private final Handler timerHandler = new Handler(Looper.getMainLooper());
    private Runnable timerRunnable;

    private long startTimeMillis = 0L;
    private Location lastAcceptedLocation = null;

    @Override
    public void onCreate() {
        super.onCreate();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        createNotificationChannel();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;

                List<Location> currentPath = pathPoints.getValue();
                if (currentPath == null) currentPath = new ArrayList<>();

                float totalDistance = distanceMeters.getValue() == null ? 0f : distanceMeters.getValue();

                for (Location location : locationResult.getLocations()) {
                    if (location == null) continue;

                    // 1) ignorar precisión mala
                    if (location.hasAccuracy() && location.getAccuracy() > 50f) {
                        continue;
                    }

                    // 2) primer punto válido
                    if (lastAcceptedLocation == null) {
                        lastAcceptedLocation = location;
                        currentPath.add(location);
                        continue;
                    }

                    float delta = lastAcceptedLocation.distanceTo(location);
                    long deltaTime = location.getTime() - lastAcceptedLocation.getTime();

                    // 3) ignorar puntos demasiado rápidos o absurdos
                    if (deltaTime > 0) {
                        float speedMps = delta / (deltaTime / 1000f);
                        if (speedMps > 8.5f) { // ~30.6 km/h, absurdo para corrida normal
                            continue;
                        }
                    }

                    // 4) ignorar micro-ruido
                    if (delta < 1f) {
                        continue;
                    }

                    // 5) ignorar saltos gigantes de GPS
                    if (delta > 150f) {
                        continue;
                    }

                    totalDistance += delta;
                    lastAcceptedLocation = location;
                    android.util.Log.d("RUN_DEBUG", "Lat: " + location.getLatitude() + " Lng: " + location.getLongitude());
                    currentPath.add(location);
                }

                distanceMeters.postValue(totalDistance);
                pathPoints.postValue(currentPath);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Boolean.TRUE.equals(isTracking.getValue())) {
            return START_STICKY;
        }

        startTimeMillis = System.currentTimeMillis();
        elapsedTimeMillis.postValue(0L);
        distanceMeters.postValue(0f);
        pathPoints.postValue(new ArrayList<>());
        lastAcceptedLocation = null;
        isTracking.postValue(true);

        startForeground(NOTIFICATION_ID, buildNotification("Preparando tracking..."));

        startTimer();
        requestLocationUpdates();

        return START_STICKY;
    }

    private void startTimer() {
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (!Boolean.TRUE.equals(isTracking.getValue())) return;

                long elapsed = System.currentTimeMillis() - startTimeMillis;
                elapsedTimeMillis.postValue(elapsed);

                float meters = distanceMeters.getValue() == null ? 0f : distanceMeters.getValue();
                updateNotification(meters, elapsed);

                timerHandler.postDelayed(this, 1000);
            }
        };

        timerHandler.post(timerRunnable);
    }

    private void requestLocationUpdates() {
        LocationRequest request = new LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                2000
        )
                .setMinUpdateDistanceMeters(2f)
                .build();

        try {
            fusedLocationClient.requestLocationUpdates(
                    request,
                    locationCallback,
                    Looper.getMainLooper()
            );
        } catch (SecurityException ignored) {
        }
    }

    private void updateNotification(float meters, long elapsedMillis) {
        String text = RunStatsUtils.formatDistanceKm(meters) + " • " + RunStatsUtils.formatDuration(elapsedMillis);

        Notification notification = buildNotification(text);
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            manager.notify(NOTIFICATION_ID, notification);
        }
    }

    private Notification buildNotification(String text) {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Running activo")
                .setContentText(text)
                .setOngoing(true)
                .build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Run Tracking",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        isTracking.postValue(false);

        if (timerRunnable != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }

        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}