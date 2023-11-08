package com.example.styleshuffle.DataModel;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.styleshuffle.R;


    public class OutfitViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageTop, imageBottom, imageShoes;
        public OutfitViewHolder(View itemView) {
            super(itemView);
            imageTop = itemView.findViewById(R.id.topImage);
            imageBottom = itemView.findViewById(R.id.bottomImage);
            imageShoes = itemView.findViewById(R.id.shoeImage);
        }
    }

