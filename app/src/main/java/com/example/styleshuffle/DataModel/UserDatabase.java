package com.example.styleshuffle.DataModel;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = ClosetItem.class,
        version = 1,
        exportSchema = false
)
public abstract class UserDatabase extends RoomDatabase {

        private static UserDatabase userDB = null;

        public abstract ItemDAO itemDAO();

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

