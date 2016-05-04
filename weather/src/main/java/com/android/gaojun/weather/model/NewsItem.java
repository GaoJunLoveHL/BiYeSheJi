package com.android.gaojun.weather.model;

import android.graphics.Bitmap;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/4/14.
 */
public class NewsItem {

    private String title;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
