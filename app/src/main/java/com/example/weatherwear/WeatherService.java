package com.example.weatherwear;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("v1/current.json")
    Call<WeatherResponse> getCurrentWeather(
            @Query("key") String apiKey,
            @Query("q") String cityName,
            @Query("aqi") String includeAqi
    );


    @GET("v1/forecast.json")
    Call<Weather> getWeatherForecast(
            @Query("key") String apiKey,
            @Query("q") String location,
            @Query("days") int days,
            @Query("aqi") String aqi,
            @Query("alerts") String alerts
    );

    @GET("v1/forecast.json")
    Call<Weather> getHourlyForecast(@Query("key") String apiKey, @Query("q") String location, @Query("days") int days);
}
