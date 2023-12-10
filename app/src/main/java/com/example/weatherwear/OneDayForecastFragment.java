package com.example.weatherwear;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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
        Call<Weather> call = weatherApiService.getWeatherForecast("fada726ea4784bcab44232343230912", "Kitchener", 2, "no", "no");

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
        Forecastday forecastday = weather.getForecast().getForecastday()[1];
        Day day = forecastday.getDay();
        Condition condition = day.getCondition();

        // Display data in TextViews
        minMaxTemperatureTextView.setText(String.format("Temperature: %.2f°C - %.2f°C", day.getMinTemp(), day.getMaxTemp()));
        windSpeedTextView.setText(String.format("Wind Speed: %.2f km/h", day.getMaxWindSpeed()));
        humidityTextView.setText(String.format("Humidity: %.2f%%", day.getAvgHumidity()));
        conditionTextView.setText("Condition: " + day.getCondition().getText());
        Picasso.get().load("https:" + condition.getIcon()).into(imageView);
        if (day.getDaily_will_it_rain() == 1) {
           showWeatherNotification("Tomorrow it will rain with min temp. of "+ day.getMinTemp());
        } else {
            showWeatherNotification("Tomorrow it will not rain with min temp. of "+ day.getMinTemp());
        }
        if (day.getDaily_will_it_snow() == 1) {
            showWeatherNotification("Tomorrow it will snow with min temp. of "+ day.getMinTemp());
        } else {
            showWeatherNotification("Tomorrow it will not snow with min temp. of "+ day.getMinTemp());
        }
    }

    private void showWeatherNotification(String weatherCondition) {
        // Create an intent for the notification
        Intent intent = new Intent(requireContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), "weather_channel")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Weather Alert")
                .setContentText("Expect " + weatherCondition)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());
    }
}