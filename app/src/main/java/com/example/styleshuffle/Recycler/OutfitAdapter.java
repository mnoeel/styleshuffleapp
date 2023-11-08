package com.example.styleshuffle.Recycler;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.styleshuffle.DataModel.Outfit;
import com.example.styleshuffle.DataModel.OutfitViewHolder;
import com.example.styleshuffle.DataModel.UserViewHolder;
import com.example.styleshuffle.R;

import java.util.List;

public class OutfitAdapter extends RecyclerView.Adapter<OutfitViewHolder> {
    private List<Outfit> outfits;

    public OutfitAdapter(List<Outfit> outfits) {
        this.outfits = outfits;
    }

    @NonNull
    @Override
    public OutfitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.outfit_item_layout, parent, false);
        return new OutfitViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OutfitViewHolder holder, int position) {
        Outfit outfit = outfits.get(position);

        // Set the images from the outfit to the ImageViews
        holder.imageTop.setImageBitmap(BitmapFactory.decodeByteArray(outfit.getTopImage(), 0, outfit.getTopImage().length));
        holder.imageBottom.setImageBitmap(BitmapFactory.decodeByteArray(outfit.getBottomImage(), 0, outfit.getBottomImage().length));
        holder.imageShoes.setImageBitmap(BitmapFactory.decodeByteArray(outfit.getShoeImage(), 0, outfit.getShoeImage().length));
    }

    public void removeOutfit(int position) {
        outfits.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return outfits.size();
    }


    }

