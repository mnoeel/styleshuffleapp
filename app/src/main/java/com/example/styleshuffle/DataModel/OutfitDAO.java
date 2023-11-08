package com.example.styleshuffle.DataModel;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OutfitDAO {
    @Query("Select  * from Outfits")
    List<Outfit> getAllOutfits();

    @Insert
    void insertOutfit(Outfit outfit);

    @Update
    void updateOutfit(Outfit outfit);

    @Delete
    void deleteOutfit(Outfit outfit);
}
