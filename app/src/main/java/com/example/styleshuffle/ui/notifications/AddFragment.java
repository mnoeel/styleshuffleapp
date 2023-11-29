package com.example.styleshuffle.ui.notifications;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.styleshuffle.DataModel.BottomItem;
import com.example.styleshuffle.DataModel.BottomItemDAO;
import com.example.styleshuffle.DataModel.RemoveBgApiClient;
import com.example.styleshuffle.DataModel.ShoeItem;
import com.example.styleshuffle.DataModel.ShoeItemDAO;
import com.example.styleshuffle.DataModel.TopItem;
import com.example.styleshuffle.DataModel.TopItemDAO;
import com.example.styleshuffle.DataModel.UserDataConverter;
import com.example.styleshuffle.DataModel.UserDatabase;
import com.example.styleshuffle.R;
import com.example.styleshuffle.databinding.FragmentAddBinding;


import java.util.concurrent.ExecutionException;


public class AddFragment extends Fragment {


    private Button saveButton;
    private FragmentAddBinding binding;
    private static final int pic_id = 123;
    Button camera_open_id;
    ImageView imageView;
    Bitmap bmpImage;
    TopItemDAO topItemDAO;
    BottomItemDAO bottomItemDAO;
    ShoeItemDAO shoeItemDAO;
    private View view;


    // Dropdown menu arrays
    String[] listColor = {"Red", "Orange", "Yellow", "Green", "Blue", "Pink", "Purple", "White", "Black", "Brown", "Gray", "Multicolor"};
    String[] listSeason = {"Winter", "Spring", "Summer", "Fall"};
    String[] listClothes = {"Tops", "Bottoms", "Shoes"};


    AutoCompleteTextView colorAutoCompleteTextView, seasonAutoCompleteTextView, clothesAutoCompleteTextView;


    ArrayAdapter<String> colorAdapter, seasonAdapter, clothesAdapter;


    private static final String REMOVE_BG_API_KEY = "asFAMjPuDokHaEk1xLj5xaoU";
    private RemoveBgApiClient removeBgApiClient;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add, container, false);


        imageView = view.findViewById(R.id.itemImage);
        ImageButton camera_open_id = view.findViewById(R.id.camera_button);
        saveButton = view.findViewById(R.id.save_button);
        topItemDAO = UserDatabase.getDBInstance(requireContext()).topItemDAO();
        bottomItemDAO = UserDatabase.getDBInstance(requireContext()).bottomItemDAO();
        shoeItemDAO = UserDatabase.getDBInstance(requireContext()).shoeItemDAO();


        // Initialize RemoveBgApiClient
        removeBgApiClient = new RemoveBgApiClient(REMOVE_BG_API_KEY);


        // Dropdown menu initialization
        colorAutoCompleteTextView = view.findViewById(R.id.colorOfItem);
        colorAdapter = new ArrayAdapter<>(requireContext(), R.layout.color_list, listColor);
        colorAutoCompleteTextView.setAdapter(colorAdapter);


        seasonAutoCompleteTextView = view.findViewById(R.id.seasonOfItem);
        seasonAdapter = new ArrayAdapter<>(requireContext(), R.layout.color_list, listSeason);
        seasonAutoCompleteTextView.setAdapter(seasonAdapter);


        clothesAutoCompleteTextView = view.findViewById(R.id.clothingItem);
        clothesAdapter = new ArrayAdapter<>(requireContext(), R.layout.color_list, listClothes);
        clothesAutoCompleteTextView.setAdapter(clothesAdapter);


        // Item selection listeners
        colorAutoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            String item = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(requireContext(), "Color: " + item, Toast.LENGTH_SHORT).show();
        });


        seasonAutoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            String item = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(requireContext(), "Season: " + item, Toast.LENGTH_SHORT).show();
        });


        clothesAutoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            String item = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(requireContext(), "Clothing: " + item, Toast.LENGTH_SHORT).show();
        });


        // Save button click listener
        saveButton.setOnClickListener(v -> saveItem());


        // Camera button click listener
        camera_open_id.setOnClickListener(v -> {
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera_intent, pic_id);
        });


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pic_id && resultCode == Activity.RESULT_OK && data != null) {
            bmpImage = (Bitmap) data.getExtras().get("data");
            if (bmpImage != null) {
                // Execute background task to remove the background
                try {
                    RemoveBgApiClient.RemoveBackgroundTask task = removeBgApiClient.new RemoveBackgroundTask();
                    byte[] removedBgBytes = task.execute(UserDataConverter.convertImage2ByteArray(bmpImage)).get();


                    if (removedBgBytes != null) {
                        // Convert the response bytes to a Bitmap
                        Bitmap removedBgBitmap = UserDataConverter.convertByteArray2Bitmap(removedBgBytes);


                        // Set the processed image to the ImageView
                        imageView.setImageBitmap(removedBgBitmap);
                    } else {
                        Log.e("AddFragment", "Background removal failed. No response bytes.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("AddFragment", "Error executing background task: " + e.getMessage());
                }
            } else {
                Log.e("AddFragment", "Error capturing image from camera.");
            }
        }
    }


    private void saveItem() {
        String selectedColor = colorAutoCompleteTextView.getText().toString();
        String selectedSeason = seasonAutoCompleteTextView.getText().toString();
        String selectedCategory = clothesAutoCompleteTextView.getText().toString();

        // Reset UI elements
        colorAutoCompleteTextView.setText("");
        seasonAutoCompleteTextView.setText("");
        clothesAutoCompleteTextView.setText("");
        imageView.setVisibility(View.GONE);

        if (TextUtils.isEmpty(selectedColor)
                || TextUtils.isEmpty(selectedSeason)
                || TextUtils.isEmpty(selectedCategory)
                || bmpImage == null) {
            Toast.makeText(requireContext(), "User Data is Missing", Toast.LENGTH_SHORT).show();
        } else {
            // Check if bmpImage is not null before using it
            if (bmpImage.getHeight() > 0 && bmpImage.getWidth() > 0) {
                // Execute background removal task to get image without background
                try {
                    RemoveBgApiClient.RemoveBackgroundTask task = removeBgApiClient.new RemoveBackgroundTask();
                    byte[] removedBgBytes = task.execute(UserDataConverter.convertImage2ByteArray(bmpImage)).get();

                    if (removedBgBytes != null) {
                        // Continue with your logic to save the item to the respective category in the database

                        switch (selectedCategory) {
                            case "Tops":
                                // Save to the tops category
                                TopItem newItem = new TopItem();
                                newItem.setColorOfItem(selectedColor);
                                newItem.setSeasonOfItem(selectedSeason);
                                newItem.setImage(removedBgBytes); // Save image without background
                                topItemDAO.insertTopItem(newItem);
                                break;
                            case "Bottoms":
                                // Save to the bottoms category
                                BottomItem newBottomItem = new BottomItem();
                                newBottomItem.setColorOfItem(selectedColor);
                                newBottomItem.setSeasonOfItem(selectedSeason);
                                newBottomItem.setImage(removedBgBytes); // Save image without background
                                bottomItemDAO.insertBottomItem(newBottomItem);
                                break;
                            case "Shoes":
                                // Save to the shoes category
                                ShoeItem newShoeItem = new ShoeItem();
                                newShoeItem.setColorOfItem(selectedColor);
                                newShoeItem.setSeasonOfItem(selectedSeason);
                                newShoeItem.setImage(removedBgBytes); // Save image without background
                                shoeItemDAO.insertShoeItem(newShoeItem);
                                break;
                            default:
                                Toast.makeText(requireContext(), "Unknown Category", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else {
                        Log.e("AddFragment", "Background removal failed. No response bytes.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("AddFragment", "Error executing background task: " + e.getMessage());
                }
            } else {
                Toast.makeText(requireContext(), "Invalid image. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

