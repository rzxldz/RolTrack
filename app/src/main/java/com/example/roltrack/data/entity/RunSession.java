package com.example.roltrack.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "run_sessions")
public class RunSession {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date;
    private long durationMillis;
    private float distanceMeters;
    private float avgPaceSecPerKm;
    private String mapImagePath;

    public RunSession(String date, long durationMillis, float distanceMeters, float avgPaceSecPerKm, String mapImagePath) {
        this.date = date;
        this.durationMillis = durationMillis;
        this.distanceMeters = distanceMeters;
        this.avgPaceSecPerKm = avgPaceSecPerKm;
        this.mapImagePath = mapImagePath;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDate() { return date; }
    public long getDurationMillis() { return durationMillis; }
    public float getDistanceMeters() { return distanceMeters; }
    public float getAvgPaceSecPerKm() { return avgPaceSecPerKm; }
    public String getMapImagePath() { return mapImagePath; }
}