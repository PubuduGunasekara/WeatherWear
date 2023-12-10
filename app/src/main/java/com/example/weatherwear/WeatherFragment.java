package com.example.weatherwear;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatherwear.databinding.FragmentWeatherBinding;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherFragment extends Fragment {

    private WeatherService weatherService;
    private FragmentWeatherBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Initialize Retrofit and WeatherService (ensure Retrofit setup is done)
        weatherService = RetrofitClient.getClient().create(WeatherService.class);

        // Replace "YOUR_API_KEY" with your actual API key
        String apiKey = "fada726ea4784bcab44232343230912";
        String cityName = "Kitchener";
        String includeAqi = "no";

        Call<WeatherResponse> call = weatherService.getCurrentWeather(apiKey, cityName, includeAqi);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        updateUI(weatherResponse);
                    }
                } else {
                    Toast.makeText(requireContext(), "not working", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                // Handle failure (e.g., network error)
                Toast.makeText(requireContext(), "error not working", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

        return view;
    }

    private void updateUI(WeatherResponse weatherResponse) {
        // Update UI components with weather data
        binding.tvDay.setText("Day Information");
        binding.tvLocation.setText("Location: " + weatherResponse.getLocation().getName());
        binding.tvCondition.setText(weatherResponse.getCurrentWeather().getCondition().getText());
        binding.tvTemperature.setText("Temperature: " + weatherResponse.getCurrentWeather().getTemperatureCelsius() + " °C");
        binding.tvHumidity.setText("Humidity: " + weatherResponse.getCurrentWeather().getHumidity() + "%");
        binding.tvWindSpeed.setText("Wind Speed: " + weatherResponse.getCurrentWeather().getWindKph() + " kph");

        Log.d("myTag", weatherResponse.getCurrentWeather().getCondition().getIcon().toString());

        String urlFromWeatherResponse = weatherResponse.getCurrentWeather().getCondition().getIcon().toString();

        String iconUrl = "https:" + urlFromWeatherResponse;
        Picasso.get().load(iconUrl).into(binding.imgWeatherIcon);

    }
}
