package com.android.gaojun.weather.database;

import android.content.Context;
import android.content.SharedPreferences;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2016/3/22.
 */
public class MySharedPreferences extends DataSupport{
    public SharedPreferences mSharedPreferences;

    public MySharedPreferences(Context context) {
        mSharedPreferences = context.getSharedPreferences("city_list", Context.MODE_PRIVATE);
    }

    public void shareString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        String cityName = mSharedPreferences.getString(key, null);
        return cityName;
    }
}
