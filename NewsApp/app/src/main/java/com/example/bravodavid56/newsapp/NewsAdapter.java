package com.example.bravodavid56.newsapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bravodavid56.newsapp.database.Contract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by bravodavid56 on 6/26/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder>{

    private ArrayList<NewsItem> mNewsItems;
    private final NewsAdapterClickHandler mClickHandler;
    private Context context;
    private Cursor cursor;


    public NewsAdapter(Cursor cursor, NewsAdapterClickHandler n) {
        mClickHandler = n;
        this.cursor = cursor;
    }


    public interface NewsAdapterClickHandler {
        void onClick(Cursor cursor,  int index);
    }



    @Override
    public NewsAdapter.NewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        // this class defines how each holder (view) will look as its passed onto the adapter
        // essentially, this method inflates each individual view

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.news_item, parent, false);
        return new NewsAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(NewsAdapter.NewsAdapterViewHolder holder, int position) {
        holder.bind(position);
        // calls the bind method; view bind() for more information
    }

    @Override
    public int getItemCount() {
        // notice we use the cursor to get a count now, since we're querying from a database
        return cursor.getCount();
    }

    class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mNewsTitle;
        private final TextView mNewsDesc;
        private final TextView mNewsDate;
        private final ImageView mNewsImage;

        public NewsAdapterViewHolder(View view) {
            super(view);
            mNewsTitle = (TextView) view.findViewById(R.id.news_title);
            mNewsDesc = (TextView) view.findViewById(R.id.news_description);
            mNewsDate = (TextView) view.findViewById(R.id.news_date);
            mNewsImage = (ImageView) view.findViewById(R.id.news_image);
            // various items in the holder represent the different items in the view itself
            // for example, we have the title, description, date, and image for each
            // newsItem displayed
            // then we set a listener on each of the items
            view.setOnClickListener(this);

        }

        public void bind(int pos) {
            cursor.moveToPosition(pos);

            // move the cursor in the database to the same position as
            // the item
            // so the itemholder is loaded with the information taken from the database
            // this occurs for each item, so each itemholder has its corresponding information
            // taken from the database
            mNewsTitle.setText(cursor.getString(
                    cursor.getColumnIndex(Contract.TABLE_COLUMNS.COLUMN_NAME_TITLE)
            ));

            mNewsDesc.setText(cursor.getString(
                    cursor.getColumnIndex(Contract.TABLE_COLUMNS.COLUMN_NAME_DESC)
            ));

            mNewsDate.setText(cursor.getString(
                    cursor.getColumnIndex(Contract.TABLE_COLUMNS.COLUMN_NAME_DATE)
            ));

            String url = cursor.getString(
                    cursor.getColumnIndex(Contract.TABLE_COLUMNS.COLUMN_NAME_IMAGE_URL)
            );

            if (url != null) {
                Picasso.with(context)
                        .load(url)
                        .into(mNewsImage);
            }

        }

        @Override
        public void onClick(View v) {
            // when clicking the item, pass the cursor and the adapterPosition
            // as arguments to the onClick method which is implemented in
            // the MainActivity
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(cursor, adapterPosition);
        }
    }

}
