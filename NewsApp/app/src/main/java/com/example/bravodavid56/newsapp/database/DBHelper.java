package com.example.bravodavid56.newsapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by bravodavid56 on 7/24/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "articles.db";
    private static final String TAG = "dbhelper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // creates the database to be used throughout the app
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryString = "CREATE TABLE " + Contract.TABLE_COLUMNS.TABLE_NAME + " ("+
                Contract.TABLE_COLUMNS._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TABLE_COLUMNS.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                Contract.TABLE_COLUMNS.COLUMN_NAME_DATE + " DATE, " +
                Contract.TABLE_COLUMNS.COLUMN_NAME_DESC + " TEXT, " +
                Contract.TABLE_COLUMNS.COLUMN_NAME_IMAGE_URL + " TEXT, " +
                Contract.TABLE_COLUMNS.COLUMN_NAME_URL + " TEXT" +
                ");";
        Log.e(TAG, queryString);
        db.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //
    }
}
