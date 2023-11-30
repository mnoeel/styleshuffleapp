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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.styleshuffle.R;
import com.example.styleshuffle.WeatherApiService;
import com.example.styleshuffle.WeatherResponse;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TempFragment extends DialogFragment {

    private EditText editTextZipCode;
    private Button buttonGetWeather;
    private TextView textViewTemperature, textViewWeatherCondition;
    private ImageView iconImageView;

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static final String API_KEY = "9c1425ddfdbc0e4b3f9626a179b22a3b"; // Replace with your actual API key


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temp, container, false);

        editTextZipCode = view.findViewById(R.id.editTextZipCode);
        buttonGetWeather = view.findViewById(R.id.buttonGetWeather);
        textViewTemperature = view.findViewById(R.id.textViewTemperature);
        textViewWeatherCondition = view.findViewById(R.id.textViewWeatherCondition);
        iconImageView = view.findViewById(R.id.iconImageView);

        //Enter button gets data from api with the zipcode
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

    //retrieves temp and condition and prints it
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
                        //temp
                        float temperatureInKelvin = weatherResponse.getMain().getTemperature();
                        float temperatureInCelsius = temperatureInKelvin - 273.15f;
                        float temperatureInFahrenheit = temperatureInCelsius * 9 / 5 + 32;
                        DecimalFormat decimalFormat = new DecimalFormat("#.#");
                        String temperatureString = decimalFormat.format(temperatureInFahrenheit);
                        Log.d("TempFragment", "Temperature: " + temperatureString);
                        textViewTemperature.setText(temperatureString + " °F");
                        //conditions
                        String weatherCondition = weatherResponse.getWeather().get(0).getDescription();
                        Log.d("TempFragment", "Weather Condition: " + weatherCondition);
                        textViewWeatherCondition.setText(weatherCondition);
                        //picture
                        String iconCode = weatherResponse.getWeather().get(0).getIcon();
                        int targetSize = 500;
                        String iconUrl = "https://openweathermap.org/img/w/" + iconCode + ".png";
                        Picasso.get().load(iconUrl).resize(targetSize, targetSize).into(iconImageView);
                    }
                }
            }


            @Override
            public void onFailure(retrofit2.Call<WeatherResponse> call, Throwable t) {
                Log.e("TempFragment", "onFailure called", t);
            }
        });
    }


}
