package com.example.styleshuffle;

// WeatherApiService.java
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("weather")
    Call<WeatherResponse> getWeather(
            @Query("zip") String zipCode,
            @Query("appid") String apiKey
    );
}
