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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.styleshuffle.DataModel.ClosetItem;
import com.example.styleshuffle.DataModel.DataConverter;
import com.example.styleshuffle.DataModel.ItemDAO;
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
    EditText color, season;
    ItemDAO itemDAO;
    private View view;





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //NotificationsViewModel notificationsViewModel =
              //  new ViewModelProvider(this).get(NotificationsViewModel.class);

        //binding = FragmentAddBinding.inflate(inflater, container, false);
       // View root = binding.getRoot();
        view = inflater.inflate(R.layout.fragment_add, container, false);

        //final TextView textView = binding.textNotifications;
        //notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        imageView = view.findViewById(R.id.itemImage);
        camera_open_id = view.findViewById(R.id.camera_button);
        color = view.findViewById(R.id.colorOfItem);
        season = view.findViewById(R.id.seasonOfItem);

        saveButton = view.findViewById(R.id.save_button);
        itemDAO = UserDatabase.getDBInstance(requireContext()).itemDAO();

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

    public void showItems(View view) {
        // Create an instance of the ClosetFragment
        ClosetFragment closetFragment = new ClosetFragment();

// Get the fragment manager and start a transaction
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

// Replace the current fragment with the ClosetFragment
        transaction.replace(R.id.closetfragment, closetFragment); // R.id.fragment_container is the ID of the layout container in your activity

// Add the transaction to the back stack (optional)
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();

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
            ClosetItem closetItem = new ClosetItem();
            closetItem.setColorOfItem(color.getText().toString());
            closetItem.setSeasonOfItem(season.getText().toString());
            closetItem.setImage(DataConverter.convertImage2ByteArray(bmpImage));
            itemDAO.insertClosetItem(closetItem);
            Toast.makeText(
                    requireContext(),
                    "Insertion successful",
                    Toast.LENGTH_SHORT
            ).show();
        } } }

