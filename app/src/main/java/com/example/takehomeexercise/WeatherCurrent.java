package com.example.takehomeexercise;

public class WeatherCurrent {

    String temp_c;
    String temp_f;

    //getter
    public String getTemp_c() {
        return temp_c;
    }

    public String getTemp_f() {
        return temp_f;
    }

    //constructor (might not be used)
    public WeatherCurrent(String temp_c, String temp_f) {
        this.temp_c = temp_c;
        this.temp_f = temp_f;
    }
}
