package com.example.styleshuffle.ui.notifications;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.styleshuffle.DataModel.BottomItem;
import com.example.styleshuffle.DataModel.BottomItemDAO;
import com.example.styleshuffle.DataModel.ShoeItemDAO;
import com.example.styleshuffle.DataModel.TopItem;
import com.example.styleshuffle.DataModel.TopItemDAO;
import com.example.styleshuffle.DataModel.UserDataConverter;
import com.example.styleshuffle.DataModel.UserDatabase;
import com.example.styleshuffle.R;
import com.example.styleshuffle.databinding.FragmentAddBinding;
import com.example.styleshuffle.ui.home.ClosetFragment;

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

    //the dropdown menu for color and array
    String[] listColor = {"Red", "Orange", "Yellow", "Green", "Blue", "Pink", "Purple", "White", "Black", "Brown", "Gray", "Multicolor"};
    String[] listSeason = {"Winter", "Spring", "Summer", "Fall"};
    String[] listClothes = {"Tops", "Bottoms", "Shoes"};

    AutoCompleteTextView colorAutoCompleteTextView;
    AutoCompleteTextView seasonAutoCompleteTextView;
    AutoCompleteTextView clothesAutoCompleteTextView;

    ArrayAdapter<String> colorAdapter;
    ArrayAdapter<String> seasonAdapter;
    ArrayAdapter<String> clothesAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add, container, false);

        imageView = view.findViewById(R.id.itemImage);
        camera_open_id = view.findViewById(R.id.camera_button);


        // Color Dropdown
        colorAutoCompleteTextView = view.findViewById(R.id.colorOfItem);
        colorAdapter = new ArrayAdapter<String>(requireContext(), R.layout.color_list, listColor);
        colorAutoCompleteTextView.setAdapter(colorAdapter);

        // Season Dropdown
        seasonAutoCompleteTextView = view.findViewById(R.id.seasonOfItem);
        seasonAdapter = new ArrayAdapter<String>(requireContext(), R.layout.color_list, listSeason);
        seasonAutoCompleteTextView.setAdapter(seasonAdapter);

        // Clothes Dropdown
        clothesAutoCompleteTextView = view.findViewById(R.id.clothingItem);
        clothesAdapter = new ArrayAdapter<String>(requireContext(), R.layout.color_list, listClothes);
        clothesAutoCompleteTextView.setAdapter(clothesAdapter);


        colorAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(requireContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        seasonAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(requireContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        clothesAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(requireContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
            }
        });



        saveButton = view.findViewById(R.id.save_button);
        topItemDAO = UserDatabase.getDBInstance(requireContext()).topItemDAO();
        bottomItemDAO = UserDatabase.getDBInstance(requireContext()).bottomItemDAO();
        shoeItemDAO = UserDatabase.getDBInstance(requireContext()).shoeItemDAO();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });

        camera_open_id.setOnClickListener(v -> {
            // Create the camera_intent ACTION_IMAGE_CAPTURE it will open the camera for capture the image
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Start the activity with camera_intent, and request pic id
            startActivityForResult(camera_intent, pic_id);
        });
        return view;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Match the request 'pic id with requestCode
        if (requestCode == pic_id) {
            // BitMap is data structure of image file which store the image in memory
            bmpImage = (Bitmap) data.getExtras().get("data");
            // Set the image in imageview for display
            imageView.setImageBitmap(bmpImage);
        }
    }

    //made this colorOfItem
    public void saveItem() {
        String selectedColor = colorAutoCompleteTextView.getText().toString();
        String selectedSeason = seasonAutoCompleteTextView.getText().toString();
        String selectedCategory = clothesAutoCompleteTextView.getText().toString();
        if (TextUtils.isEmpty(selectedColor)
                || TextUtils.isEmpty(selectedSeason)
                || TextUtils.isEmpty(selectedCategory)
                || bmpImage == null
        ) {
            Toast.makeText(requireContext(), "User Data is Missing", Toast.LENGTH_SHORT).show();
        } else {
            switch(selectedCategory) {
                case "Top":
                    TopItem topItem = new TopItem();
                    topItem.setColorOfItem(selectedColor);
                    topItem.setSeasonOfItem(selectedSeason);
                    topItem.setImage(UserDataConverter.convertImage2ByteArray(bmpImage));
                    topItemDAO.insertTopItem(topItem);
                    break;
                case "Bottom":
                    BottomItem bottomItem = new BottomItem();
                    bottomItem.setColorOfItem(selectedColor);
                    bottomItem.setSeasonOfItem(selectedSeason);
                    bottomItem.setImage(UserDataConverter.convertImage2ByteArray(bmpImage));
                    bottomItemDAO.insertBottomItem(bottomItem);
                    break;
            }
            Toast.makeText(
                    requireContext(),
                    "Insertion successful",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
