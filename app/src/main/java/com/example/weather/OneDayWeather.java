package com.example.weather;

import java.util.List;

public class OneDayWeather  {

    private String dayName;
    private String weatherIcon;
    private String weatherKind;
    private int minTemp;
    private int maxTemp;
    private String nightIcon;
    private String nightWeatherKind;
    private String hour;
    private List<OneHourWeather> dayWeatherByOur;



    public OneDayWeather(String dayName, String weatherIcon, String weatherKind, int minTemp, int maxTemp, String nightIcon, String nightWeatherKind) {
        this.weatherIcon = weatherIcon;
        this.weatherKind = weatherKind;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.dayName = dayName;
        this.nightIcon = nightIcon;
        this.nightWeatherKind = nightWeatherKind;
    }



    public String getWeatherIcon() {
        return weatherIcon;
    }


    public String getWeatherKind() {
        return weatherKind;
    }


    public int getMinTemp() {
        return minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public String getDayName() {
        return dayName;
    }

    public String getNightIcon() {
        return nightIcon;
    }

    public String getNightWeatherKind() {
        return nightWeatherKind;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
