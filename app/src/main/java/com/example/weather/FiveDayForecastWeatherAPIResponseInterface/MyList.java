package com.example.weather.FiveDayForecastWeatherAPIResponseInterface;

import java.io.Serializable;
import java.util.List;

public class MyList implements Serializable {

    public int dt ;
    public MainModel main ;
    public List<Weather> weather;
    public Clouds clouds ;
    public Wind wind ;
    public Snow snow;
    public Sys sys ;
    public String dt_txt ;

    //Custom function for take list item day;
    public String getDay(){
        return dt_txt.substring(0,10);
    }

    public int getDt() {
        return dt;
    }

    public MainModel getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public Snow getSnow() {
        return snow;
    }

    public Sys getSys() {
        return sys;
    }

    public String getDt_txt() {
        return dt_txt;
    }
}
