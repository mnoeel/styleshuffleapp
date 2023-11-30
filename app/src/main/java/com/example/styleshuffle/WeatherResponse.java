package com.example.styleshuffle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {

    @SerializedName("main")
    private Main main;

    @SerializedName("weather")
    private List<Weather> weather;

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public static class Main {
        @SerializedName("temp")
        private float temperatureInFahrenheit;

        public float getTemperature() {
            return temperatureInFahrenheit;
        }
    }

    public static class Weather {
        @SerializedName("description")
        private String description;

        @SerializedName("icon")
        private String icon; // Icon code

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }
    }
}
