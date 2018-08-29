package com.softuni.hotornot.api.models;

import com.softuni.hotornot.api.models.helper_models.LocationInfo;
import com.softuni.hotornot.api.models.helper_models.Forecast;

import java.util.List;

public class DailyForecast {

    private LocationInfo city;
    private List<Forecast> list;

    public String getLocationName() {
        return city.name;
    }

    public Forecast getForecastForDay(int dayIndex) {
        return list.get(dayIndex);
    }
}
