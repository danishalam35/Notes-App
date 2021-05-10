package com.example.mysticky;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Notes.class},version = 1)


public  abstract class UserDb extends RoomDatabase {

    private static UserDb userDb;
    public abstract UserDao userDao();


    public static synchronized UserDb getInstance(Context context){

        if (userDb==null){

            userDb= Room.databaseBuilder(context.getApplicationContext(),
                    UserDb.class,"notesdb").fallbackToDestructiveMigration().build();


        }
        return userDb;
    }
}
