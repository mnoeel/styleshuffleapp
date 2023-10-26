package com.example.styleshuffle.Recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.styleshuffle.DataModel.UserDataConverter;
import com.example.styleshuffle.DataModel.TopItem;
import com.example.styleshuffle.DataModel.TopItemDAO;
import com.example.styleshuffle.R;
import com.example.styleshuffle.DataModel.UserViewHolder;

import java.util.List;

    public class TopUserRecycler extends RecyclerView.Adapter<UserViewHolder> {

    public List<TopItem> data;
    private TopItemDAO topItemDAO;
    private boolean isDeleteMode;

    public TopUserRecycler(List<TopItem> topItems, TopItemDAO topItemDAO, boolean isDeleteMode) {
        data = topItems;
        this.topItemDAO = topItemDAO;
        this.isDeleteMode = isDeleteMode;
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
        final TopItem topItem = data.get(i);
        byte[] imageByteArray = topItem.getImage();
        if (imageByteArray != null) {
            holder.imageView.setImageBitmap(UserDataConverter.convertByteArray2Image(imageByteArray));
        }
       /* holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (isDeleteMode) {
                    // Handle long-click event in delete mode
                    // Remove the item from the adapter

                    // Remove the item from the database
                    ClosetItem itemToDelete = data.get(i);
                    itemDAO.deleteClosetItem(closetItem);
                    removeItem(holder.getAdapterPosition());
                    return true;
                } else {
                    // Handle long-click event in normal mode (if needed)
                    // Add logic for normal mode actions
                    return false;
                }
            }
        }); */
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