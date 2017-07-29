package com.example.bravodavid56.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;


import com.example.bravodavid56.newsapp.database.Contract;
import com.example.bravodavid56.newsapp.database.DBHelper;
import com.example.bravodavid56.newsapp.database.DatabaseUtils;


public class MainActivity extends AppCompatActivity implements
        NewsAdapter.NewsAdapterClickHandler, LoaderManager.LoaderCallbacks<Void> {

    private RecyclerView mRecyclerView;

    private NewsAdapter mNewsAdapter;

    private ProgressBar mProgressBar;

    // the cursor will be used to point to a spot on the database
    private Cursor cursor;

    // the database will be a reference used by the cursor to get data
    private SQLiteDatabase db;

    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        // mNewsTextView = (TextView) findViewById(R.id.news);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_news);
        // we remove the TextView because now, we're using a RecyclerView to display the text for items

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        mNewsAdapter = new NewsAdapter(cursor, this);
        // setting our NewsAdapter to the recyclerview so that the recyclerview knows what data to display


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = prefs.getBoolean("isfirst", true);
        // looks for the "isfirst" variable stored in the SharedPreferences class
        // if true, then the activity restarts the loader

        if (isFirst) {
            load();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isfirst", false);
            // edit the variable to false after accessing it
            editor.commit();
        }


        ScheduleUtils.scheduleRefresh(this);
        // schedules a service for completion; view ScheduleUtils for more information

    }

    @Override
    public void onClick(Cursor cursor, int adapterPosition) {

        cursor.moveToPosition(adapterPosition);
        String url = cursor.getString(
                cursor.getColumnIndex(Contract.TABLE_COLUMNS.COLUMN_NAME_URL));
        // on clicking an item, the cursor moves to the corresponding item in the database
        // then you can retrieve the URL for the article

        Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browser);
        // using the article obtained from above, create a new explicit intent that
        // opens the browser to the url of the article selected

    }

    @Override
    protected void onStart() {
        // called when the activity is not destroyed, and the activity is called
        // to the foreground, whereas onCreate is called only after the activity
        // has been destroyed

        super.onStart();
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        mNewsAdapter = new NewsAdapter(cursor, this);
        mRecyclerView.setAdapter(mNewsAdapter);
        // get the database, set the cursor to all items in the database, create the adapter,
        // and set the recyclerview's adapter
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu (the search bar at the top right of the screen)
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        // if the search button is clicked, call the load() function to force refresh the screen
        if (itemId == R.id.search) {
            load();
        }
        return true;
    }

    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Void>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                // when the loader is starting, indicate it is loading via progress bar
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public Void loadInBackground() {
                // this is what the loader is actually loading
                // refreshArticles() simply makes the network call to the api to refresh the
                // data in the database
                RefreshTasks.refreshArticles(MainActivity.this);
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        // after the articles are refreshed in the database, now what?
        // set the progress bar to done since we are done loading
        mProgressBar.setVisibility(View.GONE);
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        // set the cursor to the new data in the database
        // and use it to create a new adapter (now with newest data)
        // create the recycler view with the adapter, and tell the adapter that the
        // dataset has changed

        mNewsAdapter = new NewsAdapter(cursor, this);
        mRecyclerView.setAdapter(mNewsAdapter);
        mNewsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }

    public void load() {
        // forces the loader to restart so that it can display the latest information in the database
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(1, null, this).forceLoad();
    }
}
