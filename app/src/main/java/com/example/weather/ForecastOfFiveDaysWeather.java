package com.example.weather;

import java.util.List;

public class ForecastOfFiveDaysWeather {

    List<OneDayWeather> forecastdaysWeather;

    public ForecastOfFiveDaysWeather(List<OneDayWeather> forecastdaysWeather) {
        this.forecastdaysWeather = forecastdaysWeather;
    }

    public List<OneDayWeather> getForecastdaysWeather() {
        return forecastdaysWeather;
    }
}
