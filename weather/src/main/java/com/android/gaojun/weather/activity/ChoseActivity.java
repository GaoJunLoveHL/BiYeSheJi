package com.android.gaojun.weather.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.gaojun.weather.GSON.CityListDao;
import com.android.gaojun.weather.R;
import com.android.gaojun.weather.database.DatabaseManage;
import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ChoseActivity extends AppCompatActivity{
    private ListView searchList;
    private EditText ed_cityName;
    private Button btn_search;
    private Gson gson;
    private ArrayList<String> cityNames = null;
//    private ArrayList<String> cityIds = null;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.arg1){
                case 1:
                    Bundle bundle = (Bundle) msg.obj;
                    cityNames = bundle.getStringArrayList("cityName");
//                    cityIds = bundle.getStringArrayList("cityId");
                    ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(ChoseActivity.this,android.R.layout.simple_list_item_1,cityNames);
                    mAdapter.notifyDataSetChanged();
                    searchList.setAdapter(mAdapter);
                    break;

            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("选择城市");
        actionBar.setDisplayHomeAsUpEnabled(true);
        gson = new Gson();
        searchList = (ListView) findViewById(R.id.search_city_list);
        ed_cityName = (EditText) findViewById(R.id.search_city);
        btn_search = (Button) findViewById(R.id.btn_search);
        ed_cityName.addTextChangedListener(textWatcher);

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cityName = cityNames.get(position);
//                String cityId = cityIds.get(position);
                DatabaseManage databaseManage = new DatabaseManage();
                databaseManage.insert(cityName);
                Intent intent = new Intent(ChoseActivity.this,WeatherActivity.class);
                intent.putExtra("cityName",cityName);
//                intent.putExtra("cityId",cityId);
                intent.putExtra("TYPE",0);
                startActivity(intent);
                finish();
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String str = ed_cityName.getText().toString();
            Parameters para = new Parameters();
            para.put("cityname",str);
            ApiStoreSDK.execute("http://apis.baidu.com/apistore/weatherservice/citylist", ApiStoreSDK.GET,para,new ApiCallBack(){
                @Override
                public void onSuccess(int i, String s) {
                    ArrayList<String> cityNames = new ArrayList<String>();
                    ArrayList<String> cityId = new ArrayList<String>();
                    CityListDao cityList = gson.fromJson(s, CityListDao.class);
                    for (int j = 0; j < cityList.getRetData().size(); j++) {
                        cityNames.add(cityList.getRetData().get(j).getName_cn());
                        cityId.add(cityList.getRetData().get(j).getArea_id());
                    }
                    Message msg = new Message();
                    msg.arg1 = 1;
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("cityId",cityId);
                    bundle.putStringArrayList("cityName",cityNames);
                    msg.obj = bundle;
                    mHandler.sendMessage(msg);
                }

                @Override
                public void onError(int i, String s, Exception e) {
                    Log.d("ERROR",e.getLocalizedMessage());
                }
            });
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,WeatherActivity.class);
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
            Intent intent = new Intent(this,LocationActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
