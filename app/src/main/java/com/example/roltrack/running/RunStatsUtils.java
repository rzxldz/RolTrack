package com.example.roltrack.running;

public class RunStatsUtils {

    public static String formatDuration(long millis) {
        long totalSec = millis / 1000;
        long h = totalSec / 3600;
        long m = (totalSec % 3600) / 60;
        long s = totalSec % 60;

        if (h > 0) {
            return String.format("%02d:%02d:%02d", h, m, s);
        }
        return String.format("%02d:%02d", m, s);
    }

    public static String formatDistanceKm(float meters) {
        return String.format("%.2f km", meters / 1000f);
    }

    public static float calculatePaceSecPerKm(long durationMillis, float distanceMeters) {
        if (distanceMeters <= 0f) return 0f;
        float distanceKm = distanceMeters / 1000f;
        return (durationMillis / 1000f) / distanceKm;
    }

    public static String formatPace(float secPerKm) {
        if (secPerKm <= 0f || secPerKm > 1800f) return "--:-- /km";
        int min = (int) (secPerKm / 60);
        int sec = (int) (secPerKm % 60);
        return String.format("%d:%02d /km", min, sec);
    }
}