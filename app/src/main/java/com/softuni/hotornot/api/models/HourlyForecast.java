package com.softuni.hotornot.api.models;


import com.softuni.hotornot.api.models.helper_models.LocationInfo;
import com.softuni.hotornot.api.models.helper_models.ShortForecast;

import java.util.List;

public class HourlyForecast {

    private LocationInfo city;
    private List<ShortForecast> list;

    public String getLocationName() {
        return city.name;
    }

    public List<ShortForecast> getForecasts() {
        return list;
    }
}
