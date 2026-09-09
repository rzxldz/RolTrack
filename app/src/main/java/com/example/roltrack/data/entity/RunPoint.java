package com.example.roltrack.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "run_points")
public class RunPoint {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int sessionId;
    private double latitude;
    private double longitude;
    private long timestamp;

    public RunPoint(int sessionId, double latitude, double longitude, long timestamp) {
        this.sessionId = sessionId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getSessionId() { return sessionId; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public long getTimestamp() { return timestamp; }
}