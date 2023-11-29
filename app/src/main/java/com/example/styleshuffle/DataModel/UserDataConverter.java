package com.example.styleshuffle.DataModel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class UserDataConverter {

    public static Bitmap convertByteArray2Bitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
    public static byte[] convertImage2ByteArray(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            return stream.toByteArray(); }
        else {
            // Handle the case where the Bitmap is null, e.g., log an error or return an appropriate default value.
            return new byte[0];
        }
    }
    public static Bitmap convertByteArray2Image(byte[] array) {
        return BitmapFactory.decodeByteArray(array,0,array.length);
    }
}
