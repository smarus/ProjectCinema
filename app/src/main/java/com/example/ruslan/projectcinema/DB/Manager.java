package com.example.ruslan.projectcinema.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ruslan on 13.01.16.
 */
public class Manager {
    final static String LOG_TAG = "Logs";


    public static void save(SQLiteDatabase db, Movie movie) {
        Log.d(LOG_TAG, "--- Insert in mytable: ---");
        ContentValues values = new ContentValues();
        values.put(DbHelper.COL_NAME, movie.getTitle());
        values.put(DbHelper.COL_DESCRIPTION, movie.getDescription());
        values.put(DbHelper.COL_IMAGE,movie.getImageUrl());
        values.put(DbHelper.COL_TRAILER, movie.getTrailer());
        values.put(DbHelper.COL_MOVIEID,movie.getMovieid());
        long rowID = db.insert(DbHelper.DBNAME, null, values);
        Log.d(LOG_TAG, "row inserted, ID = " + rowID);
    }

    public static ArrayList<Movie> read(SQLiteDatabase db) {
        Cursor c = db.query(DbHelper.DBNAME, null, null, null, null, null, null);
        ArrayList<Movie> movieslist = new ArrayList<>();
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                // определяем номера столбцов по имени в выборке
                int id = c.getInt(c.getColumnIndex(DbHelper.COL_ID));
                String title = c.getString(c.getColumnIndex(DbHelper.COL_NAME));
                String description = c.getString(c.getColumnIndex(DbHelper.COL_DESCRIPTION));
                String image_url=c.getString(c.getColumnIndex(DbHelper.COL_IMAGE));
                String trailer = c.getString(c.getColumnIndex(DbHelper.COL_TRAILER));
                String movieid = c.getString(c.getColumnIndex(DbHelper.COL_MOVIEID));
                movieslist.add(new Movie(id,title, description, image_url, trailer,movieid));

                Log.d(LOG_TAG,
                        "ID = " + id +
                                ", name = " + title +
                                ", desc = " + description +
                                ", url " + image_url +
                                ",trailer "+trailer);
            }
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();
        return movieslist;
    }
}

