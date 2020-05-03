package com.example.android.newnewsapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity(tableName = "Note")
public class News implements Parcelable {
    /**
     * Member variables
     */
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String Content;
    private String date;
    //  private String mUrl;


    /**
     * Constructors
     */
    public News(String title, String content, String mDate) {
        this.title = title;
        this.Content = content;
        this.date = mDate;
    }


    public News() {
    }
    /**
     * Parcelable constructor
     */
    @Ignore
    protected News(Parcel in) {
        id = in.readInt();
        title = in.readString();
        Content = in.readString();
        date = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    /**
     * Getters and setters
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }
    @Ignore
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(Content);
        parcel.writeString(date);
    }
}