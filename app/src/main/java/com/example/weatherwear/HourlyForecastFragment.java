package com.example.weatherwear;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HourlyForecastFragment extends Fragment {

    private WeatherService weatherApiService;
    private RecyclerView recyclerView;

    private HourAdapter adapter;

    public HourlyForecastFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hourly_forecast, container, false);
        Retrofit retrofit = RetrofitClient.getClient();
        weatherApiService = retrofit.create(WeatherService.class);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new HourAdapter();
        recyclerView.setAdapter(adapter);
        Call<Weather> call = weatherApiService.getHourlyForecast("fada726ea4784bcab44232343230912", "Kitchener", 1);
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (response.isSuccessful()) {
                    Weather weatherResponse = response.body();
                    updateUI(weatherResponse);
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                // Handle failure
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void updateUI(Weather weatherResponse) {
        if (weatherResponse != null) {
            // Assuming you have a method in WeatherAdapter to set the data
            adapter.setHourlyForecast(weatherResponse.getForecast().getForecastday()[0].getHours());
        }
    }
}