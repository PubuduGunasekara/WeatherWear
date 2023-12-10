package com.example.weatherwear;

import static com.example.weatherwear.RetrofitClient.*;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherwear.databinding.FragmentWeatherBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherFragment extends Fragment {
    private WeatherService weatherService;
    private TextView tvDay, tvLocation, tvCondition, tvTemperature, tvHumidity, tvWindSpeed;
    private ImageView imgWeatherIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_weather, container, false);

    // Initialize UI components
    tvDay = view.findViewById(R.id.tvDay);
    tvLocation = view.findViewById(R.id.tvLocation);
    tvCondition = view.findViewById(R.id.tvCondition);
    tvTemperature = view.findViewById(R.id.tvTemperature);
    tvHumidity = view.findViewById(R.id.tvHumidity);
    tvWindSpeed = view.findViewById(R.id.tvWindSpeed);
    imgWeatherIcon = view.findViewById(R.id.imgWeatherIcon);

    // Initialize Retrofit and WeatherService (ensure Retrofit setup is done)
    weatherService = getClient().create(WeatherService.class);

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
//                    if (weatherResponse.getCurrentWeather().getCondition().getText().equals("Clear")) {
//                        image.setIm(R.draw.clear)
//                    } else if ()
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
        tvDay.setText("Day Information"); // You may replace this with actual day information
        tvLocation.setText("Location: " + weatherResponse.getLocation().getName());
        tvCondition.setText(weatherResponse.getCurrentWeather().getCondition().getText());
        tvTemperature.setText("Temperature: " + weatherResponse.getCurrentWeather().getTemperatureCelsius() + " °C");
        tvHumidity.setText("Humidity: " + weatherResponse.getCurrentWeather().getHumidity() + "%");
        tvWindSpeed.setText("Wind Speed: " + weatherResponse.getCurrentWeather().getWindKph() + " kph");

        // You may use a library like Picasso or Glide for image loading
        // Replace "weatherResponse.getCurrentWeather().getCondition().getIcon()" with the actual image URL
        // Picasso.get().load(weatherResponse.getCurrentWeather().getCondition().getIcon()).into(imgWeatherIcon);
    }
}