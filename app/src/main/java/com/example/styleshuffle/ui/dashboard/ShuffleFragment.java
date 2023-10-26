package com.example.styleshuffle.ui.dashboard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.styleshuffle.DataModel.ClosetItem;
import com.example.styleshuffle.DataModel.ItemDAO;
import com.example.styleshuffle.DataModel.UserDatabase;
import com.example.styleshuffle.R;
import com.example.styleshuffle.databinding.FragmentShuffleBinding;

import java.util.Random;

public class ShuffleFragment extends Fragment {

    private FragmentShuffleBinding binding;
    ImageView shirtImage;
    Button shuffleButton;
    View view;
    ItemDAO itemDAO;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shuffle, container, false);
        shirtImage = view.findViewById(R.id.shirtImage);
        shuffleButton = view.findViewById(R.id.shuffleButton);
        shuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemDAO = UserDatabase.getDBInstance(requireContext()).itemDAO();
                // Generate a random index
                Random random = new Random();
                int randomIndex = random.nextInt(itemDAO.getAllClosetItems().size());
                // Get the random item
                ClosetItem randomItem = itemDAO.getAllClosetItems().get(randomIndex);
                if (randomItem != null) {
                    byte[] imageBytes = randomItem.getImage();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    shirtImage.setImageBitmap(bitmap);
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