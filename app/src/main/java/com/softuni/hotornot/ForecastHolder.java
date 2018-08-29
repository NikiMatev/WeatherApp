package com.softuni.hotornot;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.softuni.hotornot.api.models.helper_models.ShortForecast;
import com.softuni.hotornot.databinding.ItemDetailWeatherBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ForecastHolder extends RecyclerView.ViewHolder {

    private ItemDetailWeatherBinding binding;

    public ForecastHolder(ItemDetailWeatherBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ShortForecast shortForecast) {
        binding.txtTodayDate.setText(new SimpleDateFormat("MM/dd HH:mm").format(new Date(shortForecast.getTimestamp())));
        binding.imgWeather.setImageResource(WeatherUtils.getImageByWeatherType(shortForecast.getWeatherType()));
        binding.grpCard.setBackgroundColor(binding.getRoot().getContext().getResources().getColor(WeatherUtils.getColorByTemperature(shortForecast.getTemperature())));
        binding.txtDescription.setText(shortForecast.getWeatherLongDescription());
        binding.txtShortWeather.setText(shortForecast.getWeatherShortDescription());
        binding.txtTemp.setText(binding.getRoot().getContext().getString(R.string.temperature_holder, (int) shortForecast.getTemperature()));
    }
}