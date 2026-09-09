package com.example.roltrack.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sleep_logs")
public class SleepLog {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String date;
    private String sleepStart;
    private String sleepEnd;
    private double hoursSlept;
    private int sleepQuality;

    public SleepLog(String date, String sleepStart, String sleepEnd, double hoursSlept, int sleepQuality) {
        this.date = date;
        this.sleepStart = sleepStart;
        this.sleepEnd = sleepEnd;
        this.hoursSlept = hoursSlept;
        this.sleepQuality = sleepQuality;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getSleepStart() { return sleepStart; }
    public void setSleepStart(String sleepStart) { this.sleepStart = sleepStart; }
    public String getSleepEnd() { return sleepEnd; }
    public void setSleepEnd(String sleepEnd) { this.sleepEnd = sleepEnd; }
    public double getHoursSlept() { return hoursSlept; }
    public void setHoursSlept(double hoursSlept) { this.hoursSlept = hoursSlept; }
    public int getSleepQuality() { return sleepQuality; }
    public void setSleepQuality(int sleepQuality) { this.sleepQuality = sleepQuality; }
}