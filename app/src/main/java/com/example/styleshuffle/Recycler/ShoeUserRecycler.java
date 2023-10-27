package com.example.styleshuffle.Recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.styleshuffle.DataModel.UserDataConverter;
import com.example.styleshuffle.DataModel.ShoeItem;
import com.example.styleshuffle.DataModel.ShoeItemDAO;
import com.example.styleshuffle.R;
import com.example.styleshuffle.DataModel.UserViewHolder;

import java.util.List;

public class ShoeUserRecycler extends RecyclerView.Adapter<UserViewHolder> {

    public List<ShoeItem> data;
    private ShoeItemDAO shoeItemDAO;
    private boolean isDeleteMode;

    public ShoeUserRecycler(List<ShoeItem> shoeItems) {
        data = shoeItems;
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
    }


    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
