package com.example.weatherwear;

public class Day {
    private double mintemp_c;
    private double maxtemp_c;
    private double avghumidity;
    private double maxwind_kph;
    private Condition condition;

    public double getMinTemp() {
        return mintemp_c;
    }

    public double getMaxTemp() {
        return maxtemp_c;
    }

    public double getAvgHumidity() {
        return avghumidity;
    }

    public double getMaxWindSpeed() {
        return maxwind_kph;
    }

    public Condition getCondition() {
        return condition;
    }
}