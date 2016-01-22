package com.example.ruslan.projectcinema.dbt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ruslan on 13.01.16.
 */
public class DbTimeHelper extends SQLiteOpenHelper {
    Context context;
    public static final  String Dbname= "DATABASE";
    public static final  String DBNAME= "MyDb";
    public static final  String COL_ID= "_id";
    public static final  String COL_TITLE= "title";
    public static final  String COL_HALL= "hall";
    public static final  String COL_THEATRE= "theatre";
    public static final  String COL_DATE = "date";
    public static final  String COL_PRICE = "price";
    public static final  String COL_MOVIE = "movie";
    public DbTimeHelper(Context context)
    {
        super(context, Dbname, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createSimpleTable(db);
    }

    private void createSimpleTable(SQLiteDatabase db) {
        String sql = "create table " + DBNAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_TITLE + " TEXT," +
                COL_THEATRE+ " TEXT," +
                COL_HALL + " TEXT," +
                COL_DATE + " TEXT,"+
                COL_MOVIE +" TEXT,"+
                COL_PRICE + " TEXT);";
        db.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

