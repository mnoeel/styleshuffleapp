package com.example.styleshuffle.DataModel;


import androidx.room.Dao; // Import from androidx package
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface ItemDAO {
    @Query("Select  * from Items")
    List<ClosetItem> getAllClosetItems();

    @Insert
    void insertClosetItem(ClosetItem closetItem);

    @Update
    void updateClosetItem(ClosetItem closetItem);

    @Delete
    void deleteClosetItem(ClosetItem closetItem);
}
