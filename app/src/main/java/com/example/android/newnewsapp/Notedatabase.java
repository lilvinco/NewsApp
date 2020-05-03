package com.example.android.newnewsapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {News.class}, version = 1, exportSchema = false)
public abstract class Notedatabase extends RoomDatabase {


    public abstract NewsDao newsDao();


    public static Notedatabase notedatabaseinstance;

    //instance of Notedatase
    public static synchronized  Notedatabase getInstance(Context context){
        if (notedatabaseinstance == null){
            notedatabaseinstance = Room.databaseBuilder(context, Notedatabase.class, "note_database")
                    .fallbackToDestructiveMigration() // for migartion such as database version changed
                    .allowMainThreadQueries()  // to allow main thread
                    .build();
        }

        return notedatabaseinstance;
    }


}
