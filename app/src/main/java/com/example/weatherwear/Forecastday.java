package com.example.weatherwear;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecastday {
    private Day day;

    @SerializedName("hour")
    private List<Hour> hours;

    public Day getDay() {
        return day;
    }

    public List<Hour> getHours() {
        return hours;
    }
}
