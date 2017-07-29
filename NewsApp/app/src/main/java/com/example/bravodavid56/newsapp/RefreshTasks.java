package com.example.bravodavid56.newsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.bravodavid56.newsapp.database.DBHelper;
import com.example.bravodavid56.newsapp.database.DatabaseUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by bravodavid56 on 7/24/2017.
 */

public class RefreshTasks {

    public static void refreshArticles(Context context) {
        ArrayList<NewsItem> result;
        // this will hold all of the NewsItems
        URL url = NetworkUtils.buildUrl();

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            // dump all the previous data from the database
            DatabaseUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            // recieves a JSON response from the URL
            result = NetworkUtils.parseJSON(json);
            // insert all of the data from the network call into the database
            DatabaseUtils.bulkInsert(db, result);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

    }
}
