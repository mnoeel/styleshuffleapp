package com.example.styleshuffle.ui.dashboard;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.styleshuffle.DataModel.BottomItem;
import com.example.styleshuffle.DataModel.BottomItemDAO;
import com.example.styleshuffle.DataModel.Item;
import com.example.styleshuffle.DataModel.Outfit;
import com.example.styleshuffle.DataModel.OutfitDAO;
import com.example.styleshuffle.DataModel.ShoeItem;
import com.example.styleshuffle.DataModel.ShoeItemDAO;
import com.example.styleshuffle.DataModel.TopItem;
import com.example.styleshuffle.DataModel.TopItemDAO;
import com.example.styleshuffle.DataModel.UserDataConverter;
import com.example.styleshuffle.DataModel.UserDatabase;
import com.example.styleshuffle.R;
import com.example.styleshuffle.databinding.FragmentShuffleBinding;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ShuffleFragment extends Fragment {
    FragmentShuffleBinding binding;
    ImageView bottomsImage,topsImage,shoesImage;
    ImageButton shuffleBottomsBtn,shuffleTopsBtn,shuffleShoesBtn;
    Button shuffleOutfitBtn,saveOutfitBtn;
    View view;
    BottomItemDAO bottomItemDAO;
    TopItemDAO topItemDAO;
    ShoeItemDAO shoeItemDAO;
    OutfitDAO outfitDAO;
    Drawable topDrawable, bottomDrawable, shoeDrawable;
    Bitmap topBitmap,bottomBitmap, shoeBitmap;

    String[] listColor = {"Red", "Orange", "Yellow", "Green", "Blue", "Pink", "Purple", "White", "Black", "Brown", "Gray", "Multicolor"};
    String[] listSeason = {"Winter", "Spring", "Summer", "Fall"};

    MultiAutoCompleteTextView topColor,topSeason,bottomColor,bottomSeason,shoeColor,shoeSeason;
    ArrayAdapter<String> colorAdapter,seasonAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shuffle, container, false);


        colorAdapter = new ArrayAdapter<String>(requireContext(), R.layout.color_list, listColor);
        seasonAdapter = new ArrayAdapter<String>(requireContext(), R.layout.color_list, listSeason);

        // Top Color Dropdown
        topColor = view.findViewById(R.id.topColorTV); //add id
        topColor.setAdapter(colorAdapter);
        topColor.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());  // Use comma as the separator

        // Top Season Dropdown
        topSeason = view.findViewById(R.id.topSeasonTV); //ADD ID
        topSeason.setAdapter(seasonAdapter);
        topSeason.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        // Bottom Color Dropdown
        bottomColor = view.findViewById(R.id.bottomColorTV); //add id
        bottomColor.setAdapter(colorAdapter);
        bottomColor.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());  // Use comma as the separator

        // Bottom Season Dropdown
        bottomSeason = view.findViewById(R.id.bottomSeasonTV); //ADD ID
        bottomSeason.setAdapter(seasonAdapter);
        bottomSeason.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        // Shoe Color Dropdown
        shoeColor = view.findViewById(R.id.shoeColorTV); //add id
        shoeColor.setAdapter(colorAdapter);
        shoeColor.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());  // Use comma as the separator

        // Shoe Season Dropdown
        shoeSeason = view.findViewById(R.id.shoeSeasonTV); //ADD ID
        shoeSeason.setAdapter(seasonAdapter);
        shoeSeason.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        //images
        bottomsImage = view.findViewById(R.id.bottomsImage);
        topsImage = view.findViewById(R.id.shirtImage);
        shoesImage = view.findViewById(R.id.shoesImage);

        //buttons
        shuffleBottomsBtn = view.findViewById(R.id.shuffleBottomsBtn);
        shuffleTopsBtn = view.findViewById(R.id.shuffleShirtsButton);
        shuffleShoesBtn = view.findViewById(R.id.shuffleShoesButton);
        shuffleOutfitBtn = view.findViewById(R.id.shuffleOutfitButton);
        saveOutfitBtn = view.findViewById(R.id.saveButton);

        //outfit db
        outfitDAO = UserDatabase.getDBInstance(requireContext()).outfitDAO();

        //save outfit button listener
        saveOutfitBtn.setOnClickListener(v-> {
            //convert images to bitmaps
            topDrawable = topsImage.getDrawable();
            topBitmap = ((BitmapDrawable) topDrawable).getBitmap();

            bottomDrawable = bottomsImage.getDrawable();
            bottomBitmap = ((BitmapDrawable) bottomDrawable).getBitmap();

            shoeDrawable = shoesImage.getDrawable();
            shoeBitmap = ((BitmapDrawable) shoeDrawable).getBitmap();

            Outfit outfit = new Outfit();
            outfit.setTopImage(UserDataConverter.convertImage2ByteArray(topBitmap));
            outfit.setBottomImage(UserDataConverter.convertImage2ByteArray(bottomBitmap));
            outfit.setShoeImage(UserDataConverter.convertImage2ByteArray(shoeBitmap));
            outfitDAO.insertOutfit(outfit);
        });

        //shuffle button listeners
        shuffleTopsBtn.setOnClickListener(v -> {
            shuffleTopsButton();
        });
        shuffleBottomsBtn.setOnClickListener(v -> {
            shuffleBottomsButton();
        });
        shuffleShoesBtn.setOnClickListener(v -> {
            shuffleShoesButton();
        });
        shuffleOutfitBtn.setOnClickListener(v -> {
            shuffleTopsButton();
            shuffleBottomsButton();;
            shuffleShoesButton();
        });
        return view;
    }

    public void shuffleTopsButton() {
        String selectedColors = topColor.getText().toString();
        List<String> selectedColorList = Arrays.asList(selectedColors.split(", "));
        String selectedSeasons = topSeason.getText().toString();
        List<String> selectedSeasonList = Arrays.asList(selectedSeasons.split(", "));
        Log.d("ShuffleFragment", "Selected Colors: " + selectedColors);
        Log.d("ShuffleFragment", "Selected Seasons: " + selectedSeasons);
        shuffleTops(selectedColorList,selectedSeasonList);
    }
    public void shuffleBottomsButton() {
        String selectedColors = bottomColor.getText().toString();
        List<String> selectedColorList = Arrays.asList(selectedColors.split(", "));
        String selectedSeasons = bottomSeason.getText().toString();
        List<String> selectedSeasonList = Arrays.asList(selectedSeasons.split(", "));
        Log.d("ShuffleFragment", "Selected Colors: " + selectedColors);
        Log.d("ShuffleFragment", "Selected Seasons: " + selectedSeasons);
        shuffleBottoms(selectedColorList,selectedSeasonList);
    }

    public void shuffleShoesButton() {
        String selectedColors = shoeColor.getText().toString();
        List<String> selectedColorList = Arrays.asList(selectedColors.split(", "));
        String selectedSeasons = shoeSeason.getText().toString();
        List<String> selectedSeasonList = Arrays.asList(selectedSeasons.split(", "));
        Log.d("ShuffleFragment", "Selected Colors: " + selectedColors);
        Log.d("ShuffleFragment", "Selected Seasons: " + selectedSeasons);
        shuffleShoes(selectedColorList,selectedSeasonList);
    }

    public void shuffleTops(List<String> selectedColors, List<String> selectedSeasons) {
        topItemDAO = UserDatabase.getDBInstance(requireContext()).topItemDAO();
        List<TopItem> topItems = topItemDAO.getAllTopItems();
        shuffleImagesWithFilters(topItems,topsImage,selectedColors,selectedSeasons);
    }
    public void shuffleBottoms(List<String> selectedColors, List<String> selectedSeasons) {
        bottomItemDAO = UserDatabase.getDBInstance(requireContext()).bottomItemDAO();
        List<BottomItem> bottomItems = bottomItemDAO.getAllBottomItems();
        shuffleImagesWithFilters(bottomItems,bottomsImage,selectedColors,selectedSeasons);
    }
    public void shuffleShoes(List<String> selectedColors, List<String> selectedSeasons) {
        shoeItemDAO = UserDatabase.getDBInstance(requireContext()).shoeItemDAO();
        List<ShoeItem> shoeItems = shoeItemDAO.getAllShoeItems();
        shuffleImagesWithFilters(shoeItems,shoesImage,selectedColors,selectedSeasons);
    }

    public void shuffleImagesWithFilters(List<? extends Item> items, ImageView imageView, List<String> selectedColors, List<String> selectedSeasons) {
        List<Item> eligibleItems = new ArrayList<>();

        for (Item item : items) {
            String itemColor = item.getColorOfItem();
            String itemSeason = item.getSeasonOfItem();

            // Check if the item matches the selected colors (if any)
            boolean colorMatch = selectedColors.isEmpty() || selectedColors.contains(itemColor);

            // Check if the item matches the selected seasons (if any)
            boolean seasonMatch = selectedSeasons.isEmpty() || selectedSeasons.contains(itemSeason);

            if (colorMatch && seasonMatch) {
                eligibleItems.add(item);
            }
        }

        if (!eligibleItems.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(eligibleItems.size());
            Item shuffledItem = eligibleItems.get(randomIndex);
            byte[] imageBytes = shuffledItem.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imageView.setImageBitmap(bitmap);
        } else {
            // If there are no preferences or no matching items, shuffle from all items
            if (items.isEmpty()) {
                Toast.makeText(
                        requireContext(),
                        "No items available",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                Random random = new Random();
                int randomIndex = random.nextInt(items.size());
                Item shuffledItem = items.get(randomIndex);
                byte[] imageBytes = shuffledItem.getImage();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                imageView.setImageBitmap(bitmap);
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
