package com.android.gaojun.weather.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.android.gaojun.weather.model.CityName;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/5/10.
 */
public class DatabaseManage {
    private SQLiteDatabase db = null;

    public DatabaseManage() {
        db = Connector.getDatabase();
    }
    public List<CityName> selectAll(){
        List<CityName> names = DataSupport.select("name").order("date desc").find(CityName.class);
        return names;
    }

    public void insert(String cityName){
        CityName name = new CityName();
        name.setName(cityName);
        name.setDate(new Date().getTime());
        name.save();
    }

    public void upDate(long time,String cityName){
        ContentValues values = new ContentValues();
        values.put("date",time);
        DataSupport.updateAll(CityName.class,values,"name = ?",cityName);
    }

    public void delete(String cityName){
        DataSupport.deleteAll(CityName.class,"name = ?",cityName);
    }

}
