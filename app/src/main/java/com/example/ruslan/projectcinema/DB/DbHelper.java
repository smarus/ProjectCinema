package com.example.ruslan.projectcinema.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ruslan on 13.01.16.
 */
public class DbHelper extends SQLiteOpenHelper {
    Context context;
    public static final  String Dbname= "MyDb";
    public static final  String DBNAME= "MyDataBase";
    public static final  String COL_ID= "_id";
    public static final  String COL_NAME= "title";
    public static final  String COL_DESCRIPTION= "description";
    public static final  String COL_IMAGE= "imageurl";
    public static final  String COL_TRAILER = "trailer";
    public DbHelper(Context context)
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
                COL_NAME + " TEXT," +
                COL_DESCRIPTION + " TEXT," +
                COL_IMAGE + " TEXT," +
                COL_TRAILER + " TEXT);";
        db.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


