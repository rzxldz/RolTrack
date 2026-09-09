package com.example.roltrack.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "water_logs")
public class WaterLog {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date;
    private String time;
    private int amountMl;

    public WaterLog(String date, String time, int amountMl) {
        this.date = date;
        this.time = time;
        this.amountMl = amountMl;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public int getAmountMl() { return amountMl; }
    public void setAmountMl(int amountMl) { this.amountMl = amountMl; }
}