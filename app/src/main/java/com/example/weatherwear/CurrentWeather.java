package com.example.weatherwear;
import com.google.gson.annotations.SerializedName;
public class CurrentWeather {
    @SerializedName("last_updated_epoch")
    private long lastUpdatedEpoch;

    @SerializedName("last_updated")
    private String lastUpdated;

    @SerializedName("temp_c")
    private double temperatureCelsius;

    @SerializedName("temp_f")
    private double temperatureFahrenheit;

    @SerializedName("is_day")
    private int isDay;

    @SerializedName("condition")
    private WeatherCondition condition;

    @SerializedName("wind_mph")
    private double windMph;

    @SerializedName("wind_kph")
    private double windKph;

    private int humidity;


    public long getLastUpdatedEpoch() {
        return lastUpdatedEpoch;
    }

    public void setLastUpdatedEpoch(long lastUpdatedEpoch) {
        this.lastUpdatedEpoch = lastUpdatedEpoch;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public double getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public void setTemperatureCelsius(double temperatureCelsius) {
        this.temperatureCelsius = temperatureCelsius;
    }

    public double getTemperatureFahrenheit() {
        return temperatureFahrenheit;
    }

    public void setTemperatureFahrenheit(double temperatureFahrenheit) {
        this.temperatureFahrenheit = temperatureFahrenheit;
    }

    public int getIsDay() {
        return isDay;
    }

    public void setIsDay(int isDay) {
        this.isDay = isDay;
    }

    public WeatherCondition getCondition() {
        return condition;
    }

    public void setCondition(WeatherCondition condition) {
        this.condition = condition;
    }

    public double getWindMph() {
        return windMph;
    }

    public void setWindMph(double windMph) {
        this.windMph = windMph;
    }
    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setWindKph(double windKph) {
        this.windKph = windKph;
    }

    public double getWindKph() {
        return windKph;
    }
}
