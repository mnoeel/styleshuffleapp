package com.example.styleshuffle.DataModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Outfits")
public class Outfit {
    @PrimaryKey(autoGenerate = true)
    int uid;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    byte [] topImage;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    byte [] bottomImage;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    byte [] shoeImage;

    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }

    public byte[] getTopImage() {
        return topImage;
    }
    public void setTopImage(byte[] topImage) {
        this.topImage = topImage;
    }

    public byte[] getBottomImage() {
        return bottomImage;
    }
    public void setBottomImage(byte[] bottomImage) {
        this.bottomImage = bottomImage;
    }

    public byte[] getShoeImage() {
        return shoeImage;
    }
    public void setShoeImage(byte[] shoeImage) {
        this.shoeImage = shoeImage;
    }






}
