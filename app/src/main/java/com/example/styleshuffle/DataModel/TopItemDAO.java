package com.example.styleshuffle.DataModel;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TopItemDAO extends ItemDAO<TopItem>{
    @Query("Select  * from TopItems")
    List<TopItem> getAllTopItems();

    @Insert
    void insertTopItem(TopItem topItem);

    @Update
    void updateTopItem(TopItem topItem);

    @Delete
    void deleteClosetItem(BottomItem bottomItem);
}
