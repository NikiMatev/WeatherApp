package com.softuni.hotornot.api.models;


import com.softuni.hotornot.api.models.helper_models.Clouds;
import com.softuni.hotornot.api.models.helper_models.GeneralInfo;
import com.softuni.hotornot.api.models.helper_models.MainMeasurements;
import com.softuni.hotornot.api.models.helper_models.Wind;

import java.util.List;

public class CurrentWeather {

    private String name;
    private List<GeneralInfo> weather;
    private MainMeasurements main;
    private Wind wind;
    private Clouds clouds;

    public String getLocationName() {
        return name;
    }

    public String getWeatherShortDescription() {
        return weather.get(0).main;
    }

    public String getWeatherLongDescription() {
        return weather.get(0).description;
    }

    public String getImageUrl() {
        return "http://openweathermap.org/img/w/" + weather.get(0).icon + ".png";
    }

    /**
     * Gives the type of weather prevailing at the location
     * @return a constant defined in @WeatherType
     */
    public int getWeatherType() {
        if(weather.get(0).id == WeatherType.CLEAR) {
            return WeatherType.CLEAR;
        } else if(weather.get(0).id / 10 == WeatherType.EXTREME) {
            return WeatherType.EXTREME;
        } else {
            return weather.get(0).id / 100;
        }
    }

    public double getTemperature() {
        return main.temp;
    }

    public double getMaxTemperature() {
        return main.temp_max;
    }

    public double getMinTemperature() {
        return main.temp_min;
    }

    public double getPressure() {
        return main.pressure;
    }

    public double getHumidity() {
        return main.humidity;
    }

    public double getWindSpeed() {
        return wind.speed;
    }

    public double getWindDirectionInDegrees() {
        return wind.deg;
    }

    public double getCloudinessInPercentage() {
        return clouds.all;
    }
}
