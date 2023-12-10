package com.example.weatherwear;
import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
        @SerializedName("location")
        private Location location;

        @SerializedName("current")
        private CurrentWeather currentWeather;

        public Location getLocation() {
            return location;
        }

        public CurrentWeather getCurrentWeather() {
            return currentWeather;
        }
    }
