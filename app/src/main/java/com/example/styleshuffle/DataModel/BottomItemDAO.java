package com.example.styleshuffle.DataModel;


import androidx.room.Dao; // Import from androidx package
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface BottomItemDAO extends ItemDAO<BottomItem> {
    @Query("Select  * from BottomItems")
    List<BottomItem> getAllBottomItems();

    @Insert
    void insertBottomItem(BottomItem bottomItem);

    @Update
    void updateClosetItem(BottomItem bottomItem);

    @Delete
    void deleteClosetItem(BottomItem bottomItem);
}
