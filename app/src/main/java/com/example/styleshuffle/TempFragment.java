// WeatherFragment.java
package com.example.styleshuffle;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.styleshuffle.R;
import com.example.styleshuffle.WeatherApiService;
import com.example.styleshuffle.WeatherResponse;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TempFragment extends DialogFragment {

    private EditText editTextZipCode;
    private Button buttonGetWeather;
    private TextView textViewTemperature;

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static final String API_KEY = "9c1425ddfdbc0e4b3f9626a179b22a3b"; // Replace with your actual API key

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temp, container, false);

        editTextZipCode = view.findViewById(R.id.editTextZipCode);
        buttonGetWeather = view.findViewById(R.id.buttonGetWeather);
        textViewTemperature = view.findViewById(R.id.textViewTemperature);

        buttonGetWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String zipCode = editTextZipCode.getText().toString();
                Log.d("TempFragment", "Button Clicked. Zip Code: " + zipCode);
                getWeatherData(zipCode);
            }
        });

        return view;
    }

    private void getWeatherData(String zipCode) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApiService apiService = retrofit.create(WeatherApiService.class);
        retrofit2.Call<WeatherResponse> retrofitCall = apiService.getWeather(zipCode, API_KEY);

        retrofitCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(retrofit2.Call<WeatherResponse> call, Response<WeatherResponse> response) {
                Log.d("TempFragment", "onResponse called");
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        Log.d("TempFragment", "API Response: " + weatherResponse.toString());
                        float temperatureInKelvin = weatherResponse.getMain().getTemperature();
                        float temperatureInCelsius = temperatureInKelvin - 273.15f;
                        float temperatureInFahrenheit = temperatureInCelsius * 9/5 + 32;
                        //float temperature = weatherResponse.getMain().getTemperature();
                        String temperatureString = temperatureInFahrenheit + "°F";
                        Log.d("TempFragment", "Temperature: " + temperatureString);
                        textViewTemperature.setText("Temperature: " + temperatureString);
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<WeatherResponse> call, Throwable t) {
                Log.e("TempFragment", "onFailure called", t);
                // Handle failure, e.g., show an error message
            }
        });
    }


}
