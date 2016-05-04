package com.android.gaojun.weather.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.gaojun.weather.R;
import com.android.gaojun.weather.model.JuHeKey;
import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;

public class TranslateActivity extends AppCompatActivity {

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
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

        translateInfo("苹果");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void translateInfo(String before){
        Parameters para = new Parameters();
        para.put("key", JuHeKey.JUHE_KEY);
        para.put("word", before);

        ApiStoreSDK.execute("http://japi.juhe.cn/youdao/dictionary.from",ApiStoreSDK.GET,
                para,new ApiCallBack(){
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onSuccess(int i, String s) {
                        Log.d("Apple",s);
                    }

                    @Override
                    public void onError(int i, String s, Exception e) {
                        super.onError(i, s, e);
                    }
                });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(TranslateActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
