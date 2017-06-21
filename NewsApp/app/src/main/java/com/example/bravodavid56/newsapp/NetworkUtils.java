package com.example.bravodavid56.newsapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by bravodavid56 on 6/19/2017.
 */


public final class NetworkUtils {

    private static final String BASE_URL = "https://newsapi.org/v1/articles";

    private static final String SOURCE_PARAM = "source";
    private static final String SORT_BY = "sortBy";
    private static final String API_PARAM = "apiKey";

    static final String source = "the-next-web";
    static final String sort = "latest";
    static final String apiKey = ""; // YOUR API KEY GOES HERE


    public static URL buildUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(SOURCE_PARAM, source)
                .appendQueryParameter(SORT_BY, sort)
                .appendQueryParameter(API_PARAM, apiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.e("NetworkUtils", url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
