package com.example.bravodavid56.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bravodavid56 on 6/26/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder>{

    private ArrayList<NewsItem> mNewsItems;
    private final NewsAdapterClickHandler mClickHandler;




    public NewsAdapter(NewsAdapterClickHandler n) {
        mClickHandler = n;
    }


    public interface NewsAdapterClickHandler {
        void onClick(String news);
    }



    @Override
    public NewsAdapter.NewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.news_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new NewsAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(NewsAdapter.NewsAdapterViewHolder holder, int position) {
        NewsItem article = mNewsItems.get(position);
        String title = article.getTitle();
        String desc = article.getDescription();
        String time = article.getTime();

        holder.mNewsTitle.setText(title);
        holder.mNewsDesc.setText(desc);
        holder.mNewsDate.setText(time);

    }

    @Override
    public int getItemCount() {
        if (mNewsItems == null) {
            return 0;
        }
        return mNewsItems.size();
    }

    class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mNewsTitle;
        public final TextView mNewsDesc;
        public final TextView mNewsDate;


        public NewsAdapterViewHolder(View view) {
            super(view);
            mNewsTitle = (TextView) view.findViewById(R.id.news_title);
            mNewsDesc = (TextView) view.findViewById(R.id.news_description);
            mNewsDate = (TextView) view.findViewById(R.id.news_date);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String weatherForDay = mNewsItems.get(adapterPosition).getUrl();
            mClickHandler.onClick(weatherForDay);
        }
    }

    public void setNewsItems(ArrayList<NewsItem> ni) {
        mNewsItems = ni;
        notifyDataSetChanged();
    }
}
