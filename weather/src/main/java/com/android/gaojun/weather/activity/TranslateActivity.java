package com.android.gaojun.weather.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.gaojun.weather.GSON.CurrencyDao;
import com.android.gaojun.weather.R;
import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.google.gson.Gson;

public class TranslateActivity extends AppCompatActivity {
    private EditText edit_from_currency;
    private Spinner spinner_from;
    private Spinner spinner_to;
    private TextView to_currency;
    private Button start;
    private TextView tv_date;
    private TextView tv_time;
    private TextView tv_text_currency;

    private String[] currency;
    private String before_currency;
    private String after_currency;
    private String amount;

    private Gson gson;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.arg1){
                case 1:
                    CurrencyDao.RetData retData = (CurrencyDao.RetData) msg.obj;
                    String date = retData.getDate();
                    String time = retData.getTime();
                    String fromCurrency = retData.getFromCurrency();
                    String toCurrency = retData.getToCurrency();
                    String amount = retData.getAmount();
                    String currency = retData.getCurrency();
                    String convertedamount = retData.getConvertedamount();
                    to_currency.setText(convertedamount);
                    tv_date.setText(date);
                    tv_time.setText(time);
                    tv_text_currency.setText(currency);
                    break;

            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Translate");
        actionBar.setDisplayHomeAsUpEnabled(true);
        init();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = edit_from_currency.getText().toString();
                currency(before_currency,after_currency,amount);
            }
        });

        spinner_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                before_currency = currency[position];
                int length = before_currency.length();
                before_currency = before_currency.substring(length-3,length);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                after_currency = currency[position];
                int length = after_currency.length();
                after_currency = after_currency.substring(length-3,length);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void init() {
        edit_from_currency = (EditText) findViewById(R.id.edit_from_currency);
        spinner_from = (Spinner) findViewById(R.id.spinner_currency);
        spinner_to = (Spinner) findViewById(R.id.spinner_currency2);
        to_currency = (TextView) findViewById(R.id.text_to_currency);
        start = (Button) findViewById(R.id.button_huan_suan);
        tv_date = (TextView) findViewById(R.id.text_date);
        tv_time = (TextView) findViewById(R.id.text_time);
        tv_text_currency = (TextView) findViewById(R.id.text_currency);

        currency = getResources().getStringArray(R.array.currency);
        gson = new Gson();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void currency(String fromCurrency,String toCurrency,String amount){
        Parameters para = new Parameters();
        para.put("fromCurrency",fromCurrency);
        para.put("toCurrency",toCurrency);
        para.put("amount",amount);

        ApiStoreSDK.execute("http://apis.baidu.com/apistore/currencyservice/currency",ApiStoreSDK.GET,para,new ApiCallBack(){
            @Override
            public void onSuccess(int i, String s) {
                CurrencyDao currencyDao = gson.fromJson(s, CurrencyDao.class);
                CurrencyDao.RetData retData = currencyDao.getRetData();


                Message msg = new Message();
                msg.arg1 = 1;
                msg.obj = retData;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(int i, String s, Exception e) {
                super.onError(i, s, e);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TranslateActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
