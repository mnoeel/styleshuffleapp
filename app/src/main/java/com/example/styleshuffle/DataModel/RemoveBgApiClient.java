package com.example.styleshuffle.DataModel;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

// ... (imports and other parts of the class)

public class RemoveBgApiClient {
    private static final String TAG = "RemoveBgApiClient";
    private static final String REMOVE_BG_API_URL = "https://api.remove.bg/v1.0/removebg";
    private final String apiKey;
    private final OkHttpClient client;

    public RemoveBgApiClient(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
    }

    public class RemoveBackgroundTask extends AsyncTask<byte[], Void, byte[]> {
        @Override
        protected byte[] doInBackground(byte[]... params) {
            try {
                Log.d(TAG, "Starting background task");

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("image_file", "image.jpg", RequestBody.create(MediaType.parse("image/*"), params[0]))
                        .addFormDataPart("size", "auto")
                        .build();

                Request request = new Request.Builder()
                        .url(REMOVE_BG_API_URL)
                        .addHeader("X-Api-Key", apiKey)
                        .post(requestBody)
                        .build();

                Log.d(TAG, "Sending API request"); // Added this line

                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "Background removal successful");
                        return response.body().bytes();
                    } else {
                        if (response.code() == 401) {
                            Log.e(TAG, "Unauthorized. Please check your API key.");
                        } else {
                            String responseBody = response.body().string();
                            Log.e(TAG, "Background removal failed. HTTP response code: " + response.code() + ", Response body: " + responseBody);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Background removal failed.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Exception in background task: " + e.getMessage());
            }



                    return null;
        }
    }

    public AsyncTask<byte[], Void, byte[]> executeRemoveBackgroundTask(byte[] imageBytes) {
        RemoveBackgroundTask task = new RemoveBackgroundTask();
        task.execute(imageBytes);
        return task;
    }
}







