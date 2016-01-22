package com.example.ruslan.projectcinema.dbt;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ruslan.projectcinema.DB.DbHelper;
import com.example.ruslan.projectcinema.DB.Movie;

import java.util.ArrayList;

/**
 * Created by ruslan on 13.01.16.
 */
public class Manager {
    final static String LOG_TAG = "Logs";


    public static void save(SQLiteDatabase db, TimeTable timeTable) {
        Log.d(LOG_TAG, "--- Insert in mytable: ---");
        ContentValues values = new ContentValues();
        values.put(DbTimeHelper.COL_TITLE,timeTable.getTitle());
        values.put(DbTimeHelper.COL_HALL,timeTable.getHall());
        values.put(DbTimeHelper.COL_THEATRE, timeTable.getTheatre());
        values.put(DbTimeHelper.COL_DATE, timeTable.getDate());
        values.put(DbTimeHelper.COL_PRICE, timeTable.getPrice());
        long rowID = db.insert(DbTimeHelper.DBNAME, null, values);
        Log.d(LOG_TAG, "row inserted, ID = " + rowID+"timetable");
    }

    public static ArrayList<TimeTable> read(SQLiteDatabase db) {
        Cursor c = db.query(DbTimeHelper.DBNAME, null, null, null, null, null, null);
        ArrayList<TimeTable> movieslist1 = new ArrayList<>();
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                // определяем номера столбцов по имени в выборке
                int id = c.getInt(c.getColumnIndex(DbTimeHelper.COL_ID));
                String title = c.getString(c.getColumnIndex(DbTimeHelper.COL_TITLE));
                String hall = c.getString(c.getColumnIndex(DbTimeHelper.COL_HALL));
                String theatre=c.getString(c.getColumnIndex(DbTimeHelper.COL_THEATRE));
                String date = c.getString(c.getColumnIndex(DbTimeHelper.COL_DATE));
                String price = c.getString(c.getColumnIndex(DbTimeHelper.COL_PRICE));
                movieslist1.add(new TimeTable(hall,title, theatre, date,price));

                Log.d(LOG_TAG,
                        "ID = " + id +
                                ", name = " + title +
                                ",theatre_title"+theatre+
                                ", desc = " + hall +
                                ", url " + theatre +
                                ",trailer "+date);
            }
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();
        return movieslist1;
    }
}
