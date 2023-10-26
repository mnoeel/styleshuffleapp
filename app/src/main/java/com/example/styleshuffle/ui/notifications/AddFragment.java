package com.example.styleshuffle.ui.notifications;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.styleshuffle.DataModel.BottomItem;
import com.example.styleshuffle.DataModel.BottomItemDAO;
import com.example.styleshuffle.DataModel.UserDataConverter;
import com.example.styleshuffle.DataModel.UserDatabase;
import com.example.styleshuffle.R;
import com.example.styleshuffle.databinding.FragmentAddBinding;

public class AddFragment extends Fragment {

    private Button saveButton;

    private FragmentAddBinding binding;
    private static final int pic_id = 123;
    Button camera_open_id;
    ImageView imageView;
    Bitmap bmpImage;
    EditText color, season;
    BottomItemDAO bottomItemDAO;
    private View view;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add, container, false);
        imageView = view.findViewById(R.id.itemImage);
        camera_open_id = view.findViewById(R.id.camera_button);
        color = view.findViewById(R.id.colorOfItem);
        season = view.findViewById(R.id.seasonOfItem);
        saveButton = view.findViewById(R.id.save_button);
        bottomItemDAO = UserDatabase.getDBInstance(requireContext()).bottomItemDAO();
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
        } }
    public void saveItem (){
        if (color.getText().toString().isEmpty()
                || season.getText().toString().isEmpty()
                || bmpImage == null
        ) {
            Toast.makeText(requireContext(), "User Data is Missing", Toast.LENGTH_SHORT).show();
        } else {
            BottomItem bottomItem = new BottomItem();
            bottomItem.setColorOfItem(color.getText().toString());
            bottomItem.setSeasonOfItem(season.getText().toString());
            bottomItem.setImage(UserDataConverter.convertImage2ByteArray(bmpImage));
            bottomItemDAO.insertBottomItem(bottomItem);
            Toast.makeText(
                    requireContext(),
                    "Insertion successful",
                    Toast.LENGTH_SHORT
            ).show();
        } } }

