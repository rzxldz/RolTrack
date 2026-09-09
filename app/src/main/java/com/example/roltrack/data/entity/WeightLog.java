package com.example.roltrack.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weight_logs")
public class WeightLog {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date;
    private double weight;

    public WeightLog(String date, double weight) {
        this.date = date;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public double getWeight() {
        return weight;
    }
}