package com.softuni.hotornot;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softuni.hotornot.api.models.HourlyForecast;
import com.softuni.hotornot.api.models.helper_models.ShortForecast;
import com.softuni.hotornot.databinding.ItemDetailWeatherBinding;

/**
 * Created by teodo on 4/6/2018.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastHolder> {

    private HourlyForecast data;

    public ForecastAdapter(HourlyForecast data) {
        this.data = data;
    }

    @Override
    public ForecastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemDetailWeatherBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_detail_weather, parent, false);
        return new ForecastHolder(binding);
    }

    @Override
    public void onBindViewHolder(ForecastHolder holder, int position) {
        holder.bind(data.getForecasts().get(position));
    }

    @Override
    public int getItemCount() {
        return data.getForecasts().size();
    }

}
