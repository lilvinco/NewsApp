package com.example.android.newnewsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.graphics.drawable.GradientDrawable;


public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, @NonNull ArrayList<News> newsItems) {
        super(context, 0, newsItems);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
        }

        News currentNews = getItem(position);

        TextView categoryText = (TextView) listItemView.findViewById(R.id.news_category);
        categoryText.setText(currentNews.getCategory());

        TextView headlineText = (TextView) listItemView.findViewById(R.id.headline);
        headlineText.setText(currentNews.getHeadline());

       // Date dateObject = new Date(currentNews.getDate());

        TextView dateText = (TextView) listItemView.findViewById(R.id.date);
        String formattedDate = formatDate(currentNews.getDate());
        dateText.setText(formattedDate);

        TextView timeText = (TextView) listItemView.findViewById(R.id.time);
        String formattedTime = formatTime(currentNews.getDate());
        timeText.setText(formattedTime);

        return listItemView;
    }


    private String formatDate(Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject){
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
