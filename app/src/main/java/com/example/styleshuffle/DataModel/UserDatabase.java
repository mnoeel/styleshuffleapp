package com.example.styleshuffle.DataModel;


import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(
        entities = {BottomItem.class,TopItem.class,ShoeItem.class,Outfit.class},
        version = 2,
        exportSchema = false
)
public abstract class UserDatabase extends RoomDatabase {

        private static UserDatabase userDB;
        public abstract TopItemDAO topItemDAO();
        public abstract BottomItemDAO bottomItemDAO();
        public abstract ShoeItemDAO shoeItemDAO();
        public abstract OutfitDAO outfitDAO();


        public static synchronized UserDatabase getDBInstance(Context context) {
            if(userDB == null) {
                userDB = Room.databaseBuilder(
                        context.getApplicationContext(),
                        UserDatabase.class,
                        "user19b2"
                ).allowMainThreadQueries().build();
            }
            return userDB;
        }

}

