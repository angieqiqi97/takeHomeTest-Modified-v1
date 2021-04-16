package com.example.takehomeexercise;

import com.google.gson.annotations.SerializedName;

public class LocationData {

    private String name;

    public String getName() {
        return name;
    }

    //need to make object of class current
    //diff name from json, so serialize it
    @SerializedName("current")
    public WeatherCurrent weatherCurrent;

    //constructor
    public LocationData(String name, WeatherCurrent weatherCurrent) {
        this.name = name;
        this.weatherCurrent = weatherCurrent;
    }

    //getter
    public WeatherCurrent getWeatherCurrent() {
        return weatherCurrent;
    }
}

