package com.softuni.hotornot.api;


import android.support.annotation.NonNull;
import android.util.Log;

import com.softuni.hotornot.api.models.CurrentWeather;
import com.softuni.hotornot.api.models.DailyForecast;
import com.softuni.hotornot.api.models.HourlyForecast;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static final String UNITS_METRIC = "metric";

    private static Api instance;
    private final WeatherService service;

    public static Api getInstance() {
        if(instance == null) {
            instance = new Api();
        }
        return instance;
    }

    private Api() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(WeatherService.BASE_URL)
                .build();

        service = retrofit.create(WeatherService.class);
    }

    public void getCurrentWeather(double latitude, double longtitude, final DataListener<CurrentWeather> listener) {
        service.getCurrentWeather(String.valueOf(latitude), String.valueOf(longtitude), WeatherService.API_KEY, UNITS_METRIC).enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                if(response.isSuccessful() && response.body() != null) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onError();
                }
            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                listener.onError();
                Log.e("TAG", "getCurrentWeather", t);
            }
        });
    }

    public void getDailyForecast(double latitude, double longtitude, final DataListener<DailyForecast> listener) {
        Log.e("TAG", "Get forecast called");
        String lat = String.format("%.2f", latitude);
        String lon = String.format("%.2f", longtitude);
        service.getDailyForecast(lat, lon, WeatherService.API_KEY, 1, UNITS_METRIC).enqueue(new Callback<DailyForecast>() {
            @Override
            public void onResponse(@NonNull Call<DailyForecast> call, @NonNull Response<DailyForecast> response) {
                if(response.isSuccessful() && response.body() != null) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onError();
                }
            }

            @Override
            public void onFailure(Call<DailyForecast> call, Throwable t) {
                listener.onError();
                Log.e("TAG", "getDailyForecast", t);
            }
        });
    }

    public void getHourlyForecast(double latitude, double longtitude, final DataListener<HourlyForecast> listener) {
        service.getHourlyForecast(String.valueOf(latitude), String.valueOf(longtitude), WeatherService.API_KEY, 10, UNITS_METRIC).enqueue(new Callback<HourlyForecast>() {
            @Override
            public void onResponse(Call<HourlyForecast> call, Response<HourlyForecast> response) {
                if(response.isSuccessful() && response.body() != null) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onError();
                }
            }

            @Override
            public void onFailure(Call<HourlyForecast> call, Throwable t) {
                listener.onError();
            }
        });
    }

    public interface DataListener<T> {
        void onSuccess(T data);
        void onError();
    }
}
