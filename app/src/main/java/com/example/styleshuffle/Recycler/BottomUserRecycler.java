package com.example.styleshuffle.Recycler;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.styleshuffle.DataModel.BottomItem;
import com.example.styleshuffle.DataModel.UserDataConverter;
import com.example.styleshuffle.DataModel.BottomItemDAO;
import com.example.styleshuffle.R;
import com.example.styleshuffle.DataModel.UserViewHolder;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;

public class BottomUserRecycler extends RecyclerView.Adapter<UserViewHolder> {
    private Context context;
    private List<BottomItem> data;
    private BottomItemDAO bottomItemDAO;
 //   private boolean isDeleteMode;

    private MediaPlayer trashSound;



    public BottomUserRecycler(Context context, List<BottomItem> bottomItems, BottomItemDAO dao) {
        data = bottomItems;
        bottomItemDAO = dao; // DAO from database
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
        final BottomItem bottomItem = data.get(i);
        byte[] imageByteArray = bottomItem.getImage();
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
                    deleteItem(bottomItem, i,view); // i is the item that is being deleted
                }
                lastClickTime = clickTime;
            }
        });
    }

    private void deleteItem(BottomItem bottom, int position, View view) {

        trashSound.start();
        // removing the positon
        data.remove(position);

        // moving the item postion
        notifyItemRemoved(position);

        // deleting from database
        bottomItemDAO.deleteClosetItem(bottom);

        // trashSound.start();

        Snackbar.make(view, "Item deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo", v -> undoDelete(bottom, position))
                .show();

    }

    private void undoDelete(BottomItem bottom, int position) {
        // Add the item back to the list at the original position
        data.add(position, bottom);

        // Notify the adapter that an item has been added
        notifyItemInserted(position);

        // You might want to add the item back to the database if needed
        bottomItemDAO.insertBottomItem(bottom);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}



