package com.android.gaojun.weather.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.gaojun.weather.Adapter.CityListAdapter;
import com.android.gaojun.weather.R;
import com.android.gaojun.weather.database.MySharedPreferences;

public class LocationActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView cityList;
    private Button addCity;
    private CityListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        cityList = (ListView) findViewById(R.id.list_city);
        addCity = (Button) findViewById(R.id.btn_chose_city);
        Intent intent = getIntent();
        String cityName = intent.getStringExtra("cityName");
        addCity.setOnClickListener(this);
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
}
