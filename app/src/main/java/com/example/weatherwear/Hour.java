package com.example.weatherwear;

import com.google.gson.annotations.SerializedName;

public class Hour {
    @SerializedName("time")
    private String time;

    @SerializedName("temp_c")
    private double temperatureC;

    @SerializedName("condition")
    private Condition condition;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTemperatureC() {
        return temperatureC;
    }

    public void setTemperatureC(double temperatureC) {
        this.temperatureC = temperatureC;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}