package com.example.weatherwear;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.weatherwear.databinding.FragmentWeatherBinding;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class WeatherFragment extends Fragment {

    private WeatherService weatherService;
    private FragmentWeatherBinding binding;

    private SharedPreferences sharedPreferences;

    private String cityNameUI = "Kitchener";

    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Obtain the ViewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Obtain the Context from the hosting Activity
        Context context = requireActivity();
        // Initialize SharedPreferences
        sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        // Clear SharedPreferences
        //clearSharedPreferences();

        init();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void clearSharedPreferences() {
        // Clear all data in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply(); // or editor.commit() for synchronous operation
    }

    private void init() {

        // Initialize Retrofit and WeatherService (ensure Retrofit setup is done)
        weatherService = RetrofitClient.getClient().create(WeatherService.class);
        // Replace "YOUR_API_KEY" with your actual API key
        String apiKey = "fada726ea4784bcab44232343230912";

        String includeAqi = "no";

        binding.locationUserInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String text = v.getText().toString();
                    if (!TextUtils.isEmpty(text)) {
                        cityNameUI = text;

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("cityName", text);
                        editor.apply(); // Apply changes

                        // Modify the global variable
                        //sharedViewModel.setGlobalVariable(cityNameUI);

                        //calling the weather services
                        Call<WeatherResponse> call = weatherService.getCurrentWeather(apiKey, cityNameUI, includeAqi);
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


                        Log.d("value", "onEditorAction: " + cityNameUI);
                        //v.setText("");
                        // Obtain the context from the view
                        Context context = v.getContext();
                        // Hide the keyboard
                        InputMethodManager imm = (InputMethodManager) ContextCompat.getSystemService(context, InputMethodManager.class);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                        return true;
                    }
                }
                return false;
            }


        });

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cityName", cityNameUI);
        editor.apply(); // Apply changes
        Call<WeatherResponse> call = weatherService.getCurrentWeather(apiKey, cityNameUI, includeAqi);
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

    /*@Override
    public void onClick(View view) {
        if (view.getId() == binding.submitUserInput.getId()) {
            cityNameUI = binding.locationUserInput.getText().toString();

            binding.locationUserInput.clearFocus();
            binding.locationUserInput.setText("");
        }
    }*/
}
