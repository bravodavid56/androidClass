package com.example.bravodavid56.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mNewsTextView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsTextView = (TextView) findViewById(R.id.news);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        // fetchNews();
    }

    private void fetchNews() {
        new FetchNewsTask().execute();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public class FetchNewsTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            URL newsUrl = NetworkUtils.buildUrl();
            try {
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl(newsUrl);

                return jsonResponse;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {
                mNewsTextView.setText(s);
                mProgressBar.setVisibility(View.GONE);
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
            mNewsTextView.setText("");

            fetchNews();

            return true;
        }
        return true;
    }
}
