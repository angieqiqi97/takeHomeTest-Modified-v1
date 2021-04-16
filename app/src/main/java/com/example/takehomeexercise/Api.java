package com.example.takehomeexercise;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("v1/current.json")  //http://api.weatherapi.com/v1/current.json?key=ff9f895b2e884d6680530135202710&q=Kuala%20Lumpur
    Call<LocationData> getWeatherData(
            @Query("key") String apiKey,
            @Query("q") String cityName
    );
}
