package com.example.bravodavid56.newsapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
    static final String apiKey = "0bb90ed6b7414eb699aa6f0177276939"; // YOUR API KEY GOES HERE


    // build the URL using the defined web address and query parameters above
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

    // read in the response as a String from the url created above
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

    // parse the response into an ArrayList of NewsItems
    public static ArrayList<NewsItem> parseJSON(String s) {
        try {
            JSONObject js = new JSONObject(s);
            JSONArray allArticles = js.getJSONArray("articles");

            ArrayList<NewsItem> newsItems = new ArrayList<NewsItem>();

            Log.e("JSON:", allArticles.toString());
            for (int i = 0; i < allArticles.length(); i++) {
                JSONObject oneItem = allArticles.getJSONObject(i);

                String authorName = oneItem.getString("author");
                String title = oneItem.getString("title");
                String description = oneItem.getString("description");

                String image_url = oneItem.getString("urlToImage");

                String url = oneItem.getString("url");
                String time = oneItem.getString("publishedAt");


               newsItems.add(new NewsItem
                       (authorName, title, description, image_url, url, time));
            }
                return newsItems;

        } catch(Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
