package com.example.weatherwear;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.ViewHolder> {
    private List<Hour> hourlyForecast = new ArrayList<>();

    public void setHourlyForecast(List<Hour> hourlyForecast) {
        this.hourlyForecast = hourlyForecast;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hourly_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hour hour = hourlyForecast.get(position);
        holder.bind(hour);
    }

    @Override
    public int getItemCount() {
        return hourlyForecast.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textHour;
        private ImageView imageWeatherIcon;
        private TextView textTemperature;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textHour = itemView.findViewById(R.id.textHour);
            imageWeatherIcon = itemView.findViewById(R.id.imageWeatherIcon);
            textTemperature = itemView.findViewById(R.id.textTemperature);
        }

        public void bind(Hour hour) {
            textHour.setText(hour.getTime().split(" ")[1]);
            Picasso.get().load("https:" + hour.getCondition().getIcon()).into(imageWeatherIcon);
            textTemperature.setText(hour.getTemperatureC() + "°C");
        }
    }
}

