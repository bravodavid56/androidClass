package com.example.bravodavid56.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsAdapter.NewsAdapterClickHandler{

    private RecyclerView mRecyclerView;

    private NewsAdapter mNewsAdapter;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // mNewsTextView = (TextView) findViewById(R.id.news);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_news);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mNewsAdapter = new NewsAdapter(this);
        mRecyclerView.setAdapter(mNewsAdapter);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        // fetchNews();
    }

    private void fetchNews() {
        new FetchNewsTask().execute();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(String news) {
        Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(news));
        startActivity(browser);
    }

    public class FetchNewsTask extends AsyncTask<String, Void, ArrayList<NewsItem>> {

        @Override
        protected ArrayList<NewsItem> doInBackground(String... params) {
            URL newsUrl = NetworkUtils.buildUrl();
            try {
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl(newsUrl);

                ArrayList<NewsItem> newsItems = NetworkUtils.parseJSON(jsonResponse);

                return newsItems;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsItem> news) {
            if (news != null) {
                mNewsAdapter.setNewsItems(news);
                mProgressBar.setVisibility(View.GONE);
                Log.e("MainActivity", news.get(0).getTitle());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.search) {
            mNewsAdapter.setNewsItems(new ArrayList<NewsItem>());
            fetchNews();
            return true;
        }
        return true;
    }
}
