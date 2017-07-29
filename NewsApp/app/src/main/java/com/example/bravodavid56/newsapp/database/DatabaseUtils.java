package com.example.bravodavid56.newsapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.bravodavid56.newsapp.NewsItem;

import java.util.ArrayList;

import static com.example.bravodavid56.newsapp.database.Contract.TABLE_COLUMNS.*;


/**
 * Created by bravodavid56 on 7/24/2017.
 */

public class DatabaseUtils {
    // defines the query for Select * from NewsItems;
    // simply gets all the items in the database
    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
          TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME_DATE + " DESC"
        );
        return cursor;
    }

    // parses the newsitems into individual pieces to insert them
    // into the database as individual records with various columns
    public static void bulkInsert(SQLiteDatabase db, ArrayList<NewsItem> newsitems) {

        db.beginTransaction();
        try {
            for (NewsItem a : newsitems) {
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_NAME_TITLE, a.getTitle());
                cv.put(COLUMN_NAME_DESC, a.getDescription());
                cv.put(COLUMN_NAME_DATE, a.getTime());
                cv.put(COLUMN_NAME_IMAGE_URL, a.getImage_url());
                cv.put(COLUMN_NAME_URL, a.getUrl());
                db.insert(TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void deleteAll(SQLiteDatabase db) {
        db.delete(TABLE_NAME, null, null);
    }
}
