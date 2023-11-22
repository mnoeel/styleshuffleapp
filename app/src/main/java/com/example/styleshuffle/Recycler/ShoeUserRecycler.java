package com.example.styleshuffle.Recycler;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.styleshuffle.DataModel.BottomItem;
import com.example.styleshuffle.DataModel.UserDataConverter;
import com.example.styleshuffle.DataModel.ShoeItem;
import com.example.styleshuffle.DataModel.ShoeItemDAO;
import com.example.styleshuffle.R;
import com.example.styleshuffle.DataModel.UserViewHolder;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ShoeUserRecycler extends RecyclerView.Adapter<UserViewHolder> {

    private Context context;
    public List<ShoeItem> data;
    private ShoeItemDAO shoeItemDAO;
 //   private boolean isDeleteMode;
    private MediaPlayer trashSound;
    public ShoeUserRecycler(Context context, List<ShoeItem> shoeItems, ShoeItemDAO dao){
        data = shoeItems;
        shoeItemDAO = dao;
        this.context = context;
        trashSound = MediaPlayer.create(context, R.raw.trashsound);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.user_item_layout,
                parent,
                false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int i) {
        final ShoeItem shoeItem = data.get(i);
        byte[] imageByteArray = shoeItem.getImage();
        if (imageByteArray != null) {
            holder.imageView.setImageBitmap(UserDataConverter.convertByteArray2Image(imageByteArray));
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            private static final long DOUBLE_CLICK_TIME_DELTA = 300; // how long between the double clicks
            private long lastClickTime = 0;

            @Override
            public void onClick(View view) {
                long clickTime = System.currentTimeMillis();
                if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                    // double click for item- then deleted
                    deleteItem(shoeItem, i,view); // i is the item that is being deleted
                }
                lastClickTime = clickTime;
            }
        });
    }
    private void deleteItem(ShoeItem shoe, int position, View view) {

        trashSound.start();
        // removing the positon
        data.remove(position);

        // moving the item postion
        notifyItemRemoved(position);

        // deleting from database
     shoeItemDAO.deleteClosetItem(shoe);

        // trashSound.start();

        Snackbar.make(view, "Item deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo", v -> undoDelete(shoe, position))
                .show();

    }
    private void undoDelete(ShoeItem shoe, int position) {
        // Add the item back to the list at the original position
        data.add(position, shoe);

        // Notify the adapter that an item has been added
        notifyItemInserted(position);

        // You might want to add the item back to the database if needed
        shoeItemDAO.insertShoeItem(shoe);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
