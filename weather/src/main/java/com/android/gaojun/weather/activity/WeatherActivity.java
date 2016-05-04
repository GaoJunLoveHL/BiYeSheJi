package com.android.gaojun.weather.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.android.gaojun.weather.GSON.CityCodeDao;
import com.android.gaojun.weather.GSON.SevenDayDao;
import com.android.gaojun.weather.R;
import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView tv;
    private TextView tv_curTemp;
    private TextView tv_type;
    private TextView tv_aqi;
    private Gson gson;
    private LineChart mLineChart;
    private String nowCityName;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.arg1) {
                case 1:
                    String cityName = msg.obj.toString();
                    nowCityName = cityName;
                    if (!cityName.isEmpty()) {
                        mLocationClient.stopLocation();
                    }
                    locationInfo(cityName);
                    break;
                case 2:
                    Bundle bundle = new Bundle();
                    bundle = (Bundle) msg.obj;
                    String cityid = bundle.getString("cityId");
                    String cityname = bundle.getString("cityName");
                    getWeatherInfo(cityid, cityname);
                    break;
                case 3:
                    List<Bundle> bundles = (List<Bundle>) msg.obj;
                    initChart(bundles);
                    tv.setText(nowCityName);
                    tv_curTemp.setText(bundles.get(2).getString("curTemp"));
                    tv_type.setText(bundles.get(2).getString("type"));
                    tv_aqi.setText("PM2.5:" + bundles.get(2).getString("aqi"));
                    tv_curTemp.setVisibility(View.VISIBLE);
                    tv_type.setVisibility(View.VISIBLE);
                    tv_aqi.setVisibility(View.VISIBLE);
                    break;
            }
            return false;
        }
    });

    private void getWeatherInfo(String cityid, String cityname) {
        Parameters para = new Parameters();
        para.put("cityname", cityname);
        para.put("cityid", cityid);
        ApiStoreSDK.execute(" http://apis.baidu.com/apistore/weatherservice/recentweathers", ApiStoreSDK.GET, para, new ApiCallBack() {
            @Override
            public void onSuccess(int i, String s) {
                SevenDayDao sevenDayDao = null;
                sevenDayDao = gson.fromJson(s, SevenDayDao.class);
                List<Bundle> bundles = new ArrayList<Bundle>();

                for (int j = 5; j <= 6; j++) {
                    Bundle histortyBundle = new Bundle();
                    //String ss = sevenDayDao.getRedData().getHistory().get(5).getWeek();
                    // String str = sevenDayDao.getRedData().getHistory().get(6).getWeek();
                    String hweek = sevenDayDao.getRedData().getHistory().get(j).getWeek();
                    String haqi = sevenDayDao.getRedData().getHistory().get(j).getAqi();
                    String hfengxiang = sevenDayDao.getRedData().getHistory().get(j).getFengxiang();
                    String hfengli = sevenDayDao.getRedData().getHistory().get(j).getFengli();
                    String hh_temp = sevenDayDao.getRedData().getHistory().get(j).getHightemp();
                    String hl_temp = sevenDayDao.getRedData().getHistory().get(j).getLowtemp();
                    String htype = sevenDayDao.getRedData().getHistory().get(j).getType();
                    histortyBundle.putString("week", hweek);
                    histortyBundle.putString("aqi", haqi);
                    histortyBundle.putString("fengxiang", hfengxiang);
                    histortyBundle.putString("fengli", hfengli);
                    histortyBundle.putString("h_temp", hh_temp);
                    histortyBundle.putString("l_temp", hl_temp);
                    histortyBundle.putString("type", htype);
                    bundles.add(histortyBundle);
                }

                Bundle todayBundle = new Bundle();
                String week = sevenDayDao.getRedData().getToday().getWeek();
                String curTemp = sevenDayDao.getRedData().getToday().getCurTemp();
                String aqi = sevenDayDao.getRedData().getToday().getAqi();
                String fengxiang = sevenDayDao.getRedData().getToday().getFengxiang();
                String fengli = sevenDayDao.getRedData().getToday().getFengli();
                String h_temp = sevenDayDao.getRedData().getToday().getHightemp();
                String l_temp = sevenDayDao.getRedData().getToday().getLowtemp();
                String type = sevenDayDao.getRedData().getToday().getType();

                todayBundle.putString("week", week);
                todayBundle.putString("curTemp", curTemp);
                todayBundle.putString("aqi", aqi);
                todayBundle.putString("fengxiang", fengxiang);
                todayBundle.putString("fengli", fengli);
                todayBundle.putString("h_temp", h_temp);
                todayBundle.putString("l_temp", l_temp);
                todayBundle.putString("type", type);

                bundles.add(todayBundle);

                for (int j = 0; j < 4; j++) {
                    Bundle forecastBundle = new Bundle();
                    String fweek = sevenDayDao.getRedData().getForecast().get(j).getWeek();
                    String ffengxiang = sevenDayDao.getRedData().getForecast().get(j).getFengxiang();
                    String ffengli = sevenDayDao.getRedData().getForecast().get(j).getFengli();
                    String fh_temp = sevenDayDao.getRedData().getForecast().get(j).getHightemp();
                    String fl_temp = sevenDayDao.getRedData().getForecast().get(j).getLowtemp();
                    String ftype = sevenDayDao.getRedData().getForecast().get(j).getType();
                    forecastBundle.putString("week", fweek);
                    forecastBundle.putString("fengxiang", ffengxiang);
                    forecastBundle.putString("fengli", ffengli);
                    forecastBundle.putString("h_temp", fh_temp);
                    forecastBundle.putString("l_temp", fl_temp);
                    forecastBundle.putString("type", ftype);
                    bundles.add(forecastBundle);
                }


                Message message = new Message();
                message.arg1 = 3;
                message.obj = bundles;
                mHandler.sendMessage(message);
            }

            @Override
            public void onError(int i, String s, Exception e) {
                super.onError(i, s, e);
            }
        });
    }

    private void locationInfo(final String cityName) {
        Parameters para = new Parameters();
        para.put("cityname", cityName);
        ApiStoreSDK.execute("http://apis.baidu.com/apistore/weatherservice/cityinfo", ApiStoreSDK.GET, para, new ApiCallBack() {
                    @Override
                    public void onSuccess(int i, String s) {
                        CityCodeDao cityCodeDao = null;
                        cityCodeDao = gson.fromJson(s, CityCodeDao.class);
                        String cityid = cityCodeDao.getRetData().getCityCode();
                        Message message = new Message();
                        message.arg1 = 2;
                        Bundle bundle = new Bundle();
                        bundle.putString("cityName", cityName);
                        bundle.putString("cityId", cityid);
                        message.obj = bundle;
                        mHandler.sendMessage(message);
                        Log.d("weather", cityid);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onError(int i, String s, Exception e) {
                        Log.d("ERROR", e.getMessage());
                    }
                }
        );
    }

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    amapLocation.getCity();//城市信息
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
                    Log.d("city", amapLocation.getCity() + "+" + amapLocation.getCityCode() + "+" + amapLocation.getAdCode());
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
            if (mLocationClient.isStarted()) {
                Message msg = new Message();
                msg.obj = amapLocation.getCity().substring(0, amapLocation.getCity().length() - 1);
                msg.arg1 = 1;
                mHandler.sendMessage(msg);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_weather);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle("BACK");
        actionBar.setDisplayHomeAsUpEnabled(true);
        // getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.my_title_layout);
        //初始化定位
        mLocationClient = new AMapLocationClient(WeatherActivity.this.getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        init();
        progressBar = (ProgressBar) findViewById(R.id.weather_progress);
        tv = (TextView) findViewById(R.id.content_text);
        tv_curTemp = (TextView) findViewById(R.id.tv_weather_temp);
        tv_curTemp.setVisibility(View.INVISIBLE);
        tv_type = (TextView) findViewById(R.id.tv_weather_type);
        tv_type.setVisibility(View.INVISIBLE);
        tv_aqi = (TextView) findViewById(R.id.tv_weather_aqi);
        tv_aqi.setVisibility(View.INVISIBLE);
        mLineChart = (LineChart) findViewById(R.id.spread_line_chart);
        //LineData mLineData = initChart(36,100);
//        Bundle bundle = getArguments();
//        if (bundle != null){
//            String cityName = bundle.getString("cityName");
//            String cityId = bundle.getString("cityId");
//            getWeatherInfo(cityId,cityName);
//            nowCityName = cityName;
//            mLocationClient.stopLocation();
//        }
        Intent intent = getIntent();
        String cityName = intent.getStringExtra("cityName");
        String cityId = intent.getStringExtra("cityId");
        if (cityName != null && cityId != null){
            getWeatherInfo(cityId, cityName);
            nowCityName = cityName;
            mLocationClient.stopLocation();
        }
        gson = new Gson();


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, LocationActivity.class);
                intent.putExtra("cityName", tv.getText());
                startActivity(intent);
            }
        });
    }

    private void init() {

//初始化定位参数
        mLocationOption = new AMapLocationClientOption();
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
//设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
//设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
//设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
//给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();
    }

    public void initChart(List<Bundle> bundles) {
        List<String> xValue = new ArrayList<>();
        List<Entry> yValue_h = new ArrayList<>();
        List<Entry> yValue_l = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (i == 2) {
                xValue.add("今天");
            } else {
                xValue.add(bundles.get(i).getString("week"));
            }
            String h_temp = bundles.get(i).getString("h_temp");
            String l_temp = bundles.get(i).getString("l_temp");
            yValue_h.add(new Entry(Integer.parseInt(h_temp.substring(0, h_temp.length() - 1)), i));
            yValue_l.add(new Entry(Integer.parseInt(l_temp.substring(0, l_temp.length() - 1)), i));
        }

        LineDataSet dataSet = new LineDataSet(yValue_h, "High");
        LineDataSet dataSet2 = new LineDataSet(yValue_l, "Low");
        ArrayList<ILineDataSet> mDataSets = new ArrayList<>();
        mDataSets.add(dataSet);
        mDataSets.add(dataSet2);
        LineData data = new LineData(xValue, mDataSets);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setDrawCubic(true);
        dataSet.setCubicIntensity(0.2f);
        dataSet.setDrawFilled(false);
        dataSet2.setDrawCubic(true);
        dataSet2.setCubicIntensity(0.2f);
        dataSet2.setDrawFilled(false);
        mLineChart.setData(data);
        mLineChart.setDescription("温度");
        mLineChart.animateY(3000);
        mLineChart.getAxisRight().setEnabled(false);
        mLineChart.getAxisLeft().setEnabled(false);
        mLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mLineChart.setDrawBorders(false);
        mLineChart.setTouchEnabled(false);//触摸
        mLineChart.setDragEnabled(false);//拖拽
        mLineChart.setScaleEnabled(false);//缩放
        Legend mLegend = mLineChart.getLegend();
        mLegend.setEnabled(false);
        progressBar.setVisibility(View.GONE);
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
            Intent intent = new Intent(WeatherActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}