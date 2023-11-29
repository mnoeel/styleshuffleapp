package com.example.styleshuffle;

// WeatherResponse.java
import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("main")
    private Main main;

    public Main getMain() {
        return main;
    }

    public static class Main {
        @SerializedName("temp")
        private float temperatureInFarenheight;

        public float getTemperature() {
            return temperatureInFarenheight;
        }
    }
}
