package com.example.weatherwear;

public class Day {
    private double mintemp_c;
    private double maxtemp_c;
    private double avghumidity;
    private double maxwind_kph;
    private Condition condition;

    private int daily_will_it_rain;
    private int daily_will_it_snow;

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


    public double getMaxwind_kph() {
        return maxwind_kph;
    }

    public int getDaily_will_it_rain() {
        return daily_will_it_rain;
    }

    public int getDaily_will_it_snow() {
        return daily_will_it_snow;
    }
}