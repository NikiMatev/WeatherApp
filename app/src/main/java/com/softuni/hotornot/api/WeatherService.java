package com.softuni.hotornot.api;

import com.softuni.hotornot.api.models.CurrentWeather;
import com.softuni.hotornot.api.models.DailyForecast;
import com.softuni.hotornot.api.models.HourlyForecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String API_KEY = "37426f016190340c55b693d9a76e5015";

    @GET("weather")
    Call<CurrentWeather> getCurrentWeather(@Query("lat") String lat, @Query("lon") String lon, @Query("APPID") String apiKet, @Query("units") String units);

    @GET("forecast/daily")
    Call<DailyForecast> getDailyForecast(@Query("lat") String lat, @Query("lon") String lon, @Query("APPID") String apiKet, @Query("cnt") int daysCount, @Query("units") String units);

    @GET("forecast")
    Call<HourlyForecast> getHourlyForecast(@Query("lat") String lat, @Query("lon") String lon, @Query("APPID") String apiKet, @Query("cnt") int hoursCount, @Query("units") String units);
}
