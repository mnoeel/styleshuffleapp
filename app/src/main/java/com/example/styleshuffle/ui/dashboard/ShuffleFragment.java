package com.example.styleshuffle.ui.dashboard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.styleshuffle.DataModel.BottomItem;
import com.example.styleshuffle.DataModel.BottomItemDAO;
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

import java.util.Random;

public class ShuffleFragment extends Fragment {
    FragmentShuffleBinding binding;
    ImageView bottomsImage,topsImage,shoesImage;
    Button shuffleBottomsBtn,shuffleTopsBtn,shuffleShoesBtn,shuffleOutfitBtn,saveOutfitBtn;
    View view;
    BottomItemDAO bottomItemDAO;
    TopItemDAO topItemDAO;
    ShoeItemDAO shoeItemDAO;
    OutfitDAO outfitDAO;
    Drawable topDrawable, bottomDrawable, shoeDrawable;
    Bitmap topBitmap,bottomBitmap, shoeBitmap;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shuffle, container, false);

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
            shuffleTops();
        });
        shuffleBottomsBtn.setOnClickListener(v -> {
            shuffleBottoms();
        });
        shuffleShoesBtn.setOnClickListener(v -> {
            shuffleShoes();
        });
        shuffleOutfitBtn.setOnClickListener(v -> {
            shuffleTops();
            shuffleBottoms();;
            shuffleShoes();
        });
        return view;
    }
    public void shuffleTops() {
        topItemDAO = UserDatabase.getDBInstance(requireContext()).topItemDAO();
        Random random = new Random();
        int randomIndex = random.nextInt(topItemDAO.getAllTopItems().size());
        TopItem randomItem = topItemDAO.getAllTopItems().get(randomIndex);
        if (randomItem != null) {
            byte[] imageBytes = randomItem.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            topsImage.setImageBitmap(bitmap);
        } else {
            Toast.makeText(
                    requireContext(),
                    "No pictures added",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
    public void shuffleBottoms() {
        bottomItemDAO = UserDatabase.getDBInstance(requireContext()).bottomItemDAO();
        Random random = new Random();
        int randomIndex = random.nextInt(bottomItemDAO.getAllBottomItems().size());
        BottomItem randomItem = bottomItemDAO.getAllBottomItems().get(randomIndex);
        if (randomItem != null) {
            byte[] imageBytes = randomItem.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            bottomsImage.setImageBitmap(bitmap);
        } else {
            Toast.makeText(
                    requireContext(),
                    "No bottoms to choose from :(",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
    public void shuffleShoes() {
        shoeItemDAO = UserDatabase.getDBInstance(requireContext()).shoeItemDAO();
        Random random = new Random();
        int randomIndex = random.nextInt(shoeItemDAO.getAllShoeItems().size());
        ShoeItem randomItem = shoeItemDAO.getAllShoeItems().get(randomIndex);
        if (randomItem != null) {
            byte[] imageBytes = randomItem.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            shoesImage.setImageBitmap(bitmap);
        } else {
            Toast.makeText(
                    requireContext(),
                    "No shoes to pick from :(",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}