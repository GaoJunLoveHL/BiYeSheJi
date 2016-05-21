package com.android.gaojun.weather.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.gaojun.weather.Adapter.CityListAdapter;
import com.android.gaojun.weather.R;
import com.android.gaojun.weather.database.DatabaseManage;
import com.android.gaojun.weather.model.CityName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocationActivity extends AppCompatActivity implements View.OnClickListener{

    private static String cityName;
    private static String locationCityName;

    private ListView cityList;
    private Button addCity;
    private List<String> names = new ArrayList<>();
    private CityListAdapter mListAdapter;
    private static DatabaseManage databaseManage;

    private static Boolean isExist = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("历史记录");
        actionBar.setDisplayHomeAsUpEnabled(true);

        cityList = (ListView) findViewById(R.id.list_city);
        addCity = (Button) findViewById(R.id.btn_chose_city);
        Intent intent = getIntent();
        cityName = intent.getStringExtra("cityName");
        locationCityName = intent.getStringExtra("locationCity");
        addCity.setOnClickListener(this);
        saveCity(cityName);

        loadList();

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(LocationActivity.this,WeatherActivity.class);
                i.putExtra("cityName",names.get(position));
                startActivity(i);
            }
        });

        cityList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String cityName = names.get(position);
                new AlertDialog.Builder(LocationActivity.this).setTitle("删除").setMessage("确认删除历史记录")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseManage.delete(cityName);
                                loadList();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
                    }
                }).show();
                return true;
            }
        });
    }

    private void loadList(){
        names.clear();
        List<CityName> cityNames =databaseManage.selectAll();
        for(CityName c: cityNames){
            names.add(c.getName());
        }
        names.add(0,locationCityName);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        arrayAdapter.notifyDataSetChanged();
        cityList.setAdapter(arrayAdapter);
    }

    public static void saveCity(String cityName){
        databaseManage = new DatabaseManage();;
        List<CityName> cityNames = databaseManage.selectAll();
        for (int i = 0;i<cityNames.size();i++){
            if (cityNames.get(i).getName().equals(cityName)){
                databaseManage.upDate(new Date().getTime(),cityName);
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_chose_city:
                Intent intent = new Intent(this,ChoseActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(LocationActivity.this,WeatherActivity.class);
            intent.putExtra("cityName",cityName);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
