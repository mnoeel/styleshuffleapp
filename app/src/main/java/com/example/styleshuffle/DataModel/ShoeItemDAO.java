package com.example.styleshuffle.DataModel;


import androidx.room.Dao; // Import from androidx package
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface ShoeItemDAO extends ItemDAO<ShoeItem>{
    @Query("Select  * from ShoeItems")
    List<ShoeItem> getAllShoeItems();

    @Insert
    void insertShoeItem(ShoeItem shoeItem);

    @Update
    void updateShoeItem(ShoeItem shoeItem);

    @Delete
    void deleteClosetItem(ShoeItem shoeItem);
}
