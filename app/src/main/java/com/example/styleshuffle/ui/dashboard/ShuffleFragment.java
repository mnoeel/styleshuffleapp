
package com.example.styleshuffle.ui.dashboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


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
import com.example.styleshuffle.TempFragment;
import com.example.styleshuffle.databinding.FragmentShuffleBinding;
import com.google.android.material.textfield.TextInputLayout;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class ShuffleFragment extends Fragment {
    FragmentShuffleBinding binding;
    ImageView bottomsImage, topsImage, shoesImage;
    ImageButton shuffleBottomsBtn, shuffleTopsBtn, shuffleShoesBtn, weatherButton;
    Button shuffleOutfitBtn, saveOutfitBtn, showFilters, clearFiltersBtn;
    View view;
    BottomItemDAO bottomItemDAO;
    TopItemDAO topItemDAO;
    ShoeItemDAO shoeItemDAO;
    OutfitDAO outfitDAO;
    Drawable topDrawable, bottomDrawable, shoeDrawable;
    Bitmap topBitmap, bottomBitmap, shoeBitmap;
    TextInputLayout topColorTIL, topSeasonTIL, bottomColorTIL, bottomSeasonTIL, shoeColorTIL, shoeSeasonTIL;
    private boolean shuffleButtonsMoved = false;
    boolean filtersAdded = false;
    String[] listColor = {"Red", "Orange", "Yellow", "Green", "Blue", "Pink", "Purple", "White", "Black", "Brown", "Gray", "Multicolor"};
    String[] listSeason = {"Winter", "Spring", "Summer", "Fall"};

    MultiAutoCompleteTextView topColor, topSeason, bottomColor, bottomSeason, shoeColor, shoeSeason;
    ArrayAdapter<String> colorAdapter, seasonAdapter;
    Set<String> selectedTopColors = new HashSet<>();
    Set<String> selectedTopSeasons = new HashSet<>();
    Set<String> selectedBottomColors = new HashSet<>();
    Set<String> selectedBottomSeasons = new HashSet<>();
    Set<String> selectedShoeColors = new HashSet<>();
    Set<String> selectedShoeSeasons = new HashSet<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shuffle, container, false);
        String topCategory = "tops";
        String bottomCategory = "bottoms";
        String shoeCategory = "shoes";


        colorAdapter = new ArrayAdapter<String>(requireContext(), R.layout.color_list, listColor);
        seasonAdapter = new ArrayAdapter<String>(requireContext(), R.layout.color_list, listSeason);


        // Top Color Dropdown
        topColor = view.findViewById(R.id.topColorTV); //add id
        topColor.setAdapter(colorAdapter);
        topColor.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());  // Use comma as the separator
        topColorTIL = view.findViewById(R.id.topColor);
        // Top Season Dropdown
        topSeason = view.findViewById(R.id.topSeasonTV); //ADD ID
        topSeason.setAdapter(seasonAdapter);
        topSeason.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        topSeasonTIL = view.findViewById(R.id.topSeason);
        // Bottom Color Dropdown
        bottomColor = view.findViewById(R.id.bottomColorTV); //add id
        bottomColor.setAdapter(colorAdapter);
        bottomColor.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());  // Use comma as the separator
        bottomColorTIL = view.findViewById(R.id.bottomColor);
        // Bottom Season Dropdown
        bottomSeason = view.findViewById(R.id.bottomSeasonTV); //ADD ID
        bottomSeason.setAdapter(seasonAdapter);
        bottomSeason.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        bottomSeasonTIL = view.findViewById(R.id.bottomSeason);
        // Shoe Color Dropdown
        shoeColor = view.findViewById(R.id.shoeColorTV); //add id
        shoeColor.setAdapter(colorAdapter);
        shoeColor.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());  // Use comma as the separator
        shoeColorTIL = view.findViewById(R.id.shoeColor);
        // Shoe Season Dropdown
        shoeSeason = view.findViewById(R.id.shoeSeasonTV); //ADD ID
        shoeSeason.setAdapter(seasonAdapter);
        shoeSeason.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        shoeSeasonTIL = view.findViewById(R.id.shoeSeason);
        //images
        bottomsImage = view.findViewById(R.id.bottomsImage);
        topsImage = view.findViewById(R.id.shirtImage);
        shoesImage = view.findViewById(R.id.shoesImage);


        //buttons
        weatherButton = view.findViewById(R.id.weatherButton);
        shuffleBottomsBtn = view.findViewById(R.id.shuffleBottomsBtn);
        shuffleTopsBtn = view.findViewById(R.id.shuffleShirtsBtn);
        shuffleShoesBtn = view.findViewById(R.id.shuffleShoesButton);
        shuffleOutfitBtn = view.findViewById(R.id.shuffleOutfitButton);
        saveOutfitBtn = view.findViewById(R.id.saveButton);
        showFilters = view.findViewById(R.id.filterButton);
        clearFiltersBtn = view.findViewById(R.id.clearFilterBtn);


        //Weather DialogFragment
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TempFragment dialogFragment = new TempFragment();
                dialogFragment.show(getChildFragmentManager(), "TempFragment");
            }

        });


        //outfit db
        outfitDAO = UserDatabase.getDBInstance(requireContext()).outfitDAO();

        clearFiltersBtn.setOnClickListener(v -> {
            clearFilters();
        });

        showFilters.setOnClickListener(v -> {
            if (filtersAdded) {
                // If filters are added, remove them
                clearFiltersBtn.setVisibility(View.GONE);
                hideFilters();
                moveButton("up");
                showFilters.setText("Add Filters");
            } else {
                // If no filters are added, add them
                clearFiltersBtn.setVisibility(View.VISIBLE);
                showFilters();
                moveButton("down");
                showFilters.setText("No Filters");
            }

            filtersAdded = !filtersAdded;
        });
        //save outfit button listener
        saveOutfitBtn.setOnClickListener(v -> {
            //convert images to bitmaps
            topDrawable = topsImage.getDrawable();
            bottomDrawable = bottomsImage.getDrawable();
            shoeDrawable = shoesImage.getDrawable();

            if (topDrawable == null || bottomDrawable == null || shoeDrawable == null) {
                // Check if any of the images is missing
                Toast.makeText(
                        requireContext(),
                        "Not a complete outfit :(",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                topBitmap = ((BitmapDrawable) topDrawable).getBitmap();
                bottomBitmap = ((BitmapDrawable) bottomDrawable).getBitmap();
                shoeBitmap = ((BitmapDrawable) shoeDrawable).getBitmap();

                Outfit outfit = new Outfit();
                outfit.setTopImage(UserDataConverter.convertImage2ByteArray(topBitmap));
                outfit.setBottomImage(UserDataConverter.convertImage2ByteArray(bottomBitmap));
                outfit.setShoeImage(UserDataConverter.convertImage2ByteArray(shoeBitmap));
                outfitDAO.insertOutfit(outfit);

                Toast.makeText(
                        requireContext(),
                        "Cute Outfit!",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });


        //shuffle button listeners
        shuffleTopsBtn.setOnClickListener(v -> {
            shuffleButton(topColor, topSeason, topCategory);
        });
        shuffleBottomsBtn.setOnClickListener(v -> {
            shuffleButton(bottomColor, bottomSeason, bottomCategory);
        });
        shuffleShoesBtn.setOnClickListener(v -> {
            shuffleButton(shoeColor, shoeSeason, shoeCategory);
        });
        shuffleOutfitBtn.setOnClickListener(v -> {
            shuffleButton(topColor, topSeason, topCategory);
            shuffleButton(bottomColor, bottomSeason, bottomCategory);
            shuffleButton(shoeColor, shoeSeason, shoeCategory);
        });
        setTextViewClickListener(R.id.topColorTV, selectedTopColors);
        setTextViewClickListener(R.id.topSeasonTV, selectedTopSeasons);
        setTextViewClickListener(R.id.bottomColorTV, selectedBottomColors);
        setTextViewClickListener(R.id.bottomSeasonTV, selectedBottomSeasons);
        setTextViewClickListener(R.id.shoeColorTV, selectedShoeColors);
        setTextViewClickListener(R.id.shoeSeasonTV, selectedShoeSeasons);

        return view;
    }


    public void showFilters() {
        topColorTIL.setVisibility(View.VISIBLE);
        topSeasonTIL.setVisibility(View.VISIBLE);
        bottomColorTIL.setVisibility(View.VISIBLE);
        bottomSeasonTIL.setVisibility(View.VISIBLE);
        shoeColorTIL.setVisibility(View.VISIBLE);
        shoeSeasonTIL.setVisibility(View.VISIBLE);
    }

    public void hideFilters() {
        topColorTIL.setVisibility(View.GONE);
        topSeasonTIL.setVisibility(View.GONE);
        bottomColorTIL.setVisibility(View.GONE);
        bottomSeasonTIL.setVisibility(View.GONE);
        shoeColorTIL.setVisibility(View.GONE);
        shoeSeasonTIL.setVisibility(View.GONE);
    }

    public void moveButton(String direction) {
        ViewGroup.MarginLayoutParams topBtnParams = (ViewGroup.MarginLayoutParams) shuffleTopsBtn.getLayoutParams();
        ViewGroup.MarginLayoutParams bottomBtnParams = (ViewGroup.MarginLayoutParams) shuffleBottomsBtn.getLayoutParams();
        ViewGroup.MarginLayoutParams shoesBtnParams = (ViewGroup.MarginLayoutParams) shuffleShoesBtn.getLayoutParams();
        ViewGroup.MarginLayoutParams newoutfitBtnParams = (ViewGroup.MarginLayoutParams) shuffleOutfitBtn.getLayoutParams();
        switch (direction) {
            case "down":
                topBtnParams.topMargin += 300;
                bottomBtnParams.topMargin += 300;
                shoesBtnParams.topMargin += 300;
                newoutfitBtnParams.leftMargin -= 500;
                break;
            case "up":
                topBtnParams.topMargin -= 300;
                bottomBtnParams.topMargin -= 300;
                shoesBtnParams.topMargin -= 300;
                newoutfitBtnParams.leftMargin += 500;
                break;
        }
        shuffleTopsBtn.setLayoutParams(topBtnParams);
        shuffleBottomsBtn.setLayoutParams(bottomBtnParams);
        shuffleShoesBtn.setLayoutParams(shoesBtnParams);
        shuffleOutfitBtn.setLayoutParams(newoutfitBtnParams);
    }

    private void setTextViewClickListener(int textViewId, Set<String> selectedPreferences) {
        MultiAutoCompleteTextView textView = view.findViewById(textViewId);
        textView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedColor = (String) parent.getItemAtPosition(position);
            if (selectedPreferences.contains(selectedColor)) {
                selectedPreferences.remove(selectedColor);
                String originalText = selectedPreferences.toString();
                String textToRemove = selectedColor;
                String[] items = originalText.split(",");
                List<String> itemList = new ArrayList<>(Arrays.asList(items));
                itemList.remove(textToRemove);
                String newText = TextUtils.join(",", itemList).replace("[", "").replace("]", "");
                ;
                textView.setText(newText);
                if (!newText.isEmpty()) {
                    textView.setText(newText);
                }
            } else {
                selectedPreferences.add(selectedColor);
                updateSelectedPreferenceTextView(textView, selectedPreferences);
            }
        });
    }


    private void updateSelectedPreferenceTextView(MultiAutoCompleteTextView textView, Set<String> selectedPreferences) {
        if (!selectedPreferences.isEmpty()) {
            textView.setText(TextUtils.join(", ", selectedPreferences));
        }
    }

    public void shuffleButton(MultiAutoCompleteTextView colorTV, MultiAutoCompleteTextView seasonTV, String category) {
        String selectedColors = colorTV.getText().toString();
        List<String> selectedColorList = Arrays.asList(selectedColors.split(", "));
        String selectedSeasons = seasonTV.getText().toString();
        List<String> selectedSeasonList = Arrays.asList(selectedSeasons.split(", "));
        Log.d("ShuffleFragment", "Selected Colors: " + selectedColors);
        Log.d("ShuffleFragment", "Selected Seasons: " + selectedSeasons);
        switch (category) {
            case "tops":
                shuffleTops(selectedColors, selectedSeasons, selectedColorList, selectedSeasonList);
                break;
            case "bottoms":
                shuffleBottoms(selectedColors, selectedSeasons, selectedColorList, selectedSeasonList);
                break;
            case "shoes":
                shuffleShoes(selectedColors, selectedSeasons, selectedColorList, selectedSeasonList);
                break;
        }
    }


    public void shuffleTops(String stringColors, String stringSeasons, List<String> selectedColors, List<String> selectedSeasons) {
        topItemDAO = UserDatabase.getDBInstance(requireContext()).topItemDAO();
        List<TopItem> topItems = topItemDAO.getAllTopItems();
        shuffleImagesWithFilters(stringColors, stringSeasons, topItems, topsImage, selectedColors, selectedSeasons);
    }

    public void shuffleBottoms(String stringColors, String stringSeasons, List<String> selectedColors, List<String> selectedSeasons) {
        bottomItemDAO = UserDatabase.getDBInstance(requireContext()).bottomItemDAO();
        List<BottomItem> bottomItems = bottomItemDAO.getAllBottomItems();
        shuffleImagesWithFilters(stringColors, stringSeasons, bottomItems, bottomsImage, selectedColors, selectedSeasons);
    }

    public void shuffleShoes(String stringColors, String stringSeasons, List<String> selectedColors, List<String> selectedSeasons) {
        shoeItemDAO = UserDatabase.getDBInstance(requireContext()).shoeItemDAO();
        List<ShoeItem> shoeItems = shoeItemDAO.getAllShoeItems();
        shuffleImagesWithFilters(stringColors, stringSeasons, shoeItems, shoesImage, selectedColors, selectedSeasons);
    }


    public void shuffleImagesWithFilters(String stringColors, String stringSeasons, List<? extends Item> items, ImageView imageView, List<String> selectedColors, List<String> selectedSeasons) {
        List<Item> eligibleItems = new ArrayList<>();
        List<Item> colorMatchItems = new ArrayList<>();
        List<Item> seasonMatchItems = new ArrayList<>();


        if (TextUtils.isEmpty(stringColors) && TextUtils.isEmpty(stringSeasons)) {
            for (Item item : items) {
                eligibleItems.add(item);
            }
            shuffleImages(imageView, eligibleItems);
        } else if (!TextUtils.isEmpty(stringColors) && TextUtils.isEmpty(stringSeasons)) {
            for (Item item : items) {
                String itemColor = item.getColorOfItem();
                if (selectedColors.contains(itemColor)) {
                    colorMatchItems.add(item);
                }
            }
            shuffleImages(imageView, colorMatchItems);
        } else if (TextUtils.isEmpty(stringColors) && !TextUtils.isEmpty(stringSeasons)) {
            for (Item item : items) {
                String itemSeason = item.getSeasonOfItem();
                if (selectedSeasons.contains(itemSeason)) {
                    seasonMatchItems.add(item);
                }
            }
            shuffleImages(imageView, seasonMatchItems);
        } else {
            for (Item item : items) {
                String itemColor = item.getColorOfItem();
                String itemSeason = item.getSeasonOfItem();
                boolean colorMatch = selectedColors.contains(itemColor);
                boolean seasonMatch = selectedSeasons.contains(itemSeason);
                if (colorMatch && seasonMatch) {
                    eligibleItems.add(item);
                }
            }
            shuffleImages(imageView, eligibleItems);
        }
    }


    public void shuffleImages(ImageView imageView, List<Item> eligibleItems) {
        if (!eligibleItems.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(eligibleItems.size());
            Item shuffledItem = eligibleItems.get(randomIndex);
            byte[] imageBytes = shuffledItem.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageDrawable(null);
            Toast.makeText(
                    requireContext(),
                    "Not enough items",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    public void clearFilters() {
        topColor.setText("");
        bottomColor.setText("");
        shoeColor.setText("");
        topSeason.setText("");
        bottomSeason.setText("");
        shoeSeason.setText("");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
