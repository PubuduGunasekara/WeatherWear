package com.example.weatherwear;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class OneDayForecastFragment extends Fragment {


    private WeatherService weatherApiService;

    private TextView minMaxTemperatureTextView;
    private TextView windSpeedTextView;
    private TextView humidityTextView;
    private TextView conditionTextView;

    private ImageView imageView;
    public OneDayForecastFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one_day_forecast, container, false);
        Retrofit retrofit = RetrofitClient.getClient();
        weatherApiService = retrofit.create(WeatherService.class);

        // Initialize TextViews
        minMaxTemperatureTextView = view.findViewById(R.id.minMaxTemperature);
        windSpeedTextView = view.findViewById(R.id.windSpeed);
        humidityTextView = view.findViewById(R.id.humidity);
        conditionTextView = view.findViewById(R.id.condition);
        imageView = view.findViewById(R.id.image_condition);

        // Make API call
        getWeatherData();
        return view;
    }

    private void getWeatherData() {
        // Replace "YOUR_API_KEY" with your actual API key
        Call<Weather> call = weatherApiService.getWeatherForecast("fada726ea4784bcab44232343230912", "Kitchener", 1, "no", "no");

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Update TextViews with weather information
                    Weather weather = response.body();
                    displayWeatherData(weather);
                } else {
                    // Handle API error
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                // Handle network or other errors
            }
        });
    }

    private void displayWeatherData(Weather weather) {
        Forecastday forecastday = weather.getForecast().getForecastday()[0];
        Day day = forecastday.getDay();
        Condition condition = day.getCondition();

        // Display data in TextViews
        minMaxTemperatureTextView.setText(String.format("Temperature: %.2f°C - %.2f°C", day.getMinTemp(), day.getMaxTemp()));
        windSpeedTextView.setText(String.format("Wind Speed: %.2f km/h", day.getMaxWindSpeed()));
        humidityTextView.setText(String.format("Humidity: %.2f%%", day.getAvgHumidity()));
        conditionTextView.setText("Condition: " + day.getCondition().getText());
        Picasso.get().load("https:" + condition.getIcon()).into(imageView);

    }
}