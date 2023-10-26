package com.example.styleshuffle.DataModel;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.styleshuffle.R;

public class UserViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.itemImage);
    }
}
