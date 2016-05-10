package com.android.gaojun.weather.model;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by Administrator on 2016/3/22.
 */
public class CityName extends DataSupport{
    private String name;
    private long date;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
