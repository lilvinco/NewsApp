package com.example.android.newnewsapp;

import java.util.Date;

public class News {
    /**
     * Member variables
     */
    private String mCategory;
    private String mHeadline;
    private Date mDate;
    private String mUrl;


    /**
     * Constructors
     */
    public News(String mCategory, String mHeadline, Date mDate, String mUrl) {
        this.mCategory = mCategory;
        this.mHeadline = mHeadline;
        this.mDate = mDate;
        this.mUrl = mUrl;
    }


    /**
     * Getters and setters
     */
    public String getCategory() {
        return mCategory;
    }

    public String getHeadline() {
        return mHeadline;
    }

    public Date getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
}