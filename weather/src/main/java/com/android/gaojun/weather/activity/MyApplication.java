package com.android.gaojun.weather.activity;

import android.app.Application;

import com.baidu.apistore.sdk.ApiStoreSDK;

import org.litepal.LitePalApplication;

/**
 * Created by Administrator on 2016/3/21.
 */
public class MyApplication extends LitePalApplication {
    @Override
    public void onCreate() {
        ApiStoreSDK.init(this,"f271ba093fa6af430ec83ac64fc22c30");
        super.onCreate();
    }
}
