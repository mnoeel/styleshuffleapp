package com.example.styleshuffle.ui.dashboard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.styleshuffle.DataModel.ShoeItem;
import com.example.styleshuffle.DataModel.ShoeItemDAO;
import com.example.styleshuffle.DataModel.TopItem;
import com.example.styleshuffle.DataModel.TopItemDAO;
import com.example.styleshuffle.DataModel.UserDatabase;
import com.example.styleshuffle.R;
import com.example.styleshuffle.databinding.FragmentShuffleBinding;

import java.util.Random;

public class ShuffleFragment extends Fragment {

    private FragmentShuffleBinding binding;
    ImageView bottomsImage;
    ImageView topsImage;
    ImageView shoesImage;
    Button shuffleBottomsBtn;
    Button shuffleTopsBtn;
    Button shuffleShoesBtn;
    View view;
    BottomItemDAO bottomItemDAO;
    TopItemDAO topItemDAO;
    ShoeItemDAO shoeItemDAO;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shuffle, container, false);
        bottomsImage = view.findViewById(R.id.bottomsImage);
        topsImage = view.findViewById(R.id.shirtImage);
        shoesImage = view.findViewById(R.id.shoesImage);
        shuffleBottomsBtn = view.findViewById(R.id.shuffleBottomsBtn);
        shuffleTopsBtn = view.findViewById(R.id.shuffleShirtsButton);
        shuffleShoesBtn = view.findViewById(R.id.shuffleShoesButton);
        shuffleTopsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        shuffleBottomsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            "No pictures added",
                            Toast.LENGTH_SHORT
                    ).show();
                }

            }
        });
        shuffleShoesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            "No pictures added",
                            Toast.LENGTH_SHORT
                    ).show();
                }

            }
        });

        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}