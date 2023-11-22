package com.example.styleshuffle.Recycler;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.styleshuffle.DataModel.BottomItem;
import com.example.styleshuffle.DataModel.Outfit;
import com.example.styleshuffle.DataModel.OutfitDAO;
import com.example.styleshuffle.DataModel.OutfitViewHolder;
import com.example.styleshuffle.DataModel.UserViewHolder;
import com.example.styleshuffle.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class OutfitAdapter extends RecyclerView.Adapter<OutfitViewHolder> {
private List<Outfit> outfits;
private Context context;
private OutfitDAO outfitDAO;
private MediaPlayer trashSound;
        Outfit outfit;

public OutfitAdapter(Context context, List<Outfit> outfits,OutfitDAO dao) {
        this.outfits = outfits;
        outfitDAO = dao;
        this.context=context;
        trashSound = MediaPlayer.create(context, R.raw.trashsound);
        }

@NonNull
@Override
public OutfitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.outfit_item_layout, parent, false);
        return new OutfitViewHolder(itemView);
        }

@Override
public void onBindViewHolder(@NonNull OutfitViewHolder holder, int i) {
        final Outfit outfit = outfits.get(i);
        byte[] imageByteArray = outfit.getAllImages();
        if (imageByteArray != null) {
        // Set the images from the outfit to the ImageViews
        holder.imageTop.setImageBitmap(BitmapFactory.decodeByteArray(outfit.getTopImage(), 0, outfit.getTopImage().length));
        holder.imageBottom.setImageBitmap(BitmapFactory.decodeByteArray(outfit.getBottomImage(), 0, outfit.getBottomImage().length));
        holder.imageShoes.setImageBitmap(BitmapFactory.decodeByteArray(outfit.getShoeImage(), 0, outfit.getShoeImage().length));
        }
        holder.imageTop.setOnClickListener(new View.OnClickListener() {
private static final long DOUBLE_CLICK_TIME_DELTA = 300; // how long between the double clicks
private long lastClickTime = 0;
@Override
public void onClick(View view) {
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
        // double click for item- then deleted
        deleteItem(outfit,i,view); // i is the item that is being deleted
        }
        lastClickTime = clickTime;
        }
        });
        holder.imageBottom.setOnClickListener(new View.OnClickListener() {
private static final long DOUBLE_CLICK_TIME_DELTA = 300; // how long between the double clicks
private long lastClickTime = 0;
@Override
public void onClick(View view) {
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
        // double click for item- then deleted
        deleteItem(outfit,i,view); // i is the item that is being deleted
        }
        lastClickTime = clickTime;
        }
        });
        holder.imageShoes.setOnClickListener(new View.OnClickListener() {
private static final long DOUBLE_CLICK_TIME_DELTA = 300; // how long between the double clicks
private long lastClickTime = 0;
@Override
public void onClick(View view) {
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
        // double click for item- then deleted
        deleteItem(outfit,i,view); // i is the item that is being deleted
        }
        lastClickTime = clickTime;
        }
        });
        }

private void deleteItem(Outfit outfit, int position, View view) {

        trashSound.start();
        // removing the positon
        outfits.remove(position);

        // moving the item postion
        notifyItemRemoved(position);

        // deleting from database
        outfitDAO.deleteOutfit(outfit);

        // trashSound.start();

        Snackbar.make(view, "Item deleted", Snackbar.LENGTH_LONG)
        .setAction("Undo", v -> undoDelete(outfit, position))
        .show();

        }

private void undoDelete(Outfit outfit, int position) {
        // Add the item back to the list at the original position
        outfits.add(position, outfit);

        // Notify the adapter that an item has been added
        notifyItemInserted(position);

        // You might want to add the item back to the database if needed
        outfitDAO.insertOutfit(outfit);
        }





@Override
public int getItemCount() {
        return outfits.size();
        }


        }

