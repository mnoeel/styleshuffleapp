package com.example.styleshuffle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.styleshuffle.DataModel.ClosetItem;
import com.example.styleshuffle.DataModel.DataConverter;
import com.example.styleshuffle.DataModel.ItemDAO;

import java.util.List;

public class UserRecycler extends RecyclerView.Adapter<UserViewHolder> {

    public List<ClosetItem> data;
    private ItemDAO itemDAO;
    private boolean isDeleteMode;

    public UserRecycler(List<ClosetItem> closetItems,ItemDAO itemDAO, boolean isDeleteMode) {
        data = closetItems;
        this.itemDAO = itemDAO;
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
        final ClosetItem closetItem = data.get(i);
        byte[] imageByteArray = closetItem.getImage();
        if (imageByteArray != null) {
            holder.imageView.setImageBitmap(DataConverter.convertByteArray2Image(imageByteArray));
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (isDeleteMode) {
                    // Handle long-click event in delete mode
                    // Remove the item from the adapter
                    removeItem(i);

                    // Remove the item from the database
                    itemDAO.deleteClosetItem(closetItem);
                    return true;
                } else {
                    // Handle long-click event in normal mode (if needed)
                    // Add logic for normal mode actions
                    return false;
                }
            }
        });
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
