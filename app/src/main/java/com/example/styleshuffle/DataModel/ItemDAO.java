package com.example.styleshuffle.DataModel;


import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface ItemDAO<T extends Item> {
    @Query("SELECT * FROM TopItems")
    List<TopItem> getAllTopItems();

    @Query("SELECT * FROM BottomItems")
    List<BottomItem> getAllBottomItems();

    @Query("SELECT * FROM ShoeItems")
    List<ShoeItem> getAllShoeItems();
}

