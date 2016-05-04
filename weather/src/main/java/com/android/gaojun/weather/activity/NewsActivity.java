package com.android.gaojun.weather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.gaojun.weather.Adapter.NewsPagerAdapter;
import com.android.gaojun.weather.R;
import com.android.gaojun.weather.fragment.JokeFragment;
import com.android.gaojun.weather.fragment.WxhotFragment;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private TextView tv_wxhot;
    private TextView tv_joke;
    private List<Fragment> fragments;
    private NewsPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("News");
        actionBar.setDisplayHomeAsUpEnabled(true);
        initTextView();
    }

    private void initTextView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager_news);
        tv_wxhot = (TextView) findViewById(R.id.text_view_wxhot);
        tv_joke = (TextView) findViewById(R.id.text_view_joke);

        tv_wxhot.setOnClickListener(this);
        tv_joke.setOnClickListener(this);

        tv_wxhot.setTextColor(getResources().getColor(android.R.color.holo_green_light));
        tv_joke.setTextColor(getResources().getColor(android.R.color.black));

        fragments = new ArrayList<>();

        Fragment wxhotFragment = new WxhotFragment(this);
        Fragment jokeFragment = new JokeFragment(this);

        fragments.add(wxhotFragment);
        fragments.add(jokeFragment);

        mPagerAdapter = new NewsPagerAdapter(getSupportFragmentManager(),fragments);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.setCurrentItem(position);
                if (position == 0){
                    tv_wxhot.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                    tv_joke.setTextColor(getResources().getColor(android.R.color.black));
                }else {
                    tv_joke.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                    tv_wxhot.setTextColor(getResources().getColor(android.R.color.black));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(NewsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.text_view_wxhot) {
            mViewPager.setCurrentItem(0);
            tv_wxhot.setTextColor(getResources().getColor(android.R.color.holo_green_light));
            tv_joke.setTextColor(getResources().getColor(android.R.color.black));
        } else if (id == R.id.text_view_joke) {
            mViewPager.setCurrentItem(1);
            tv_joke.setTextColor(getResources().getColor(android.R.color.holo_green_light));
            tv_wxhot.setTextColor(getResources().getColor(android.R.color.black));
        }
    }

    @Override
    public void onBackPressed() {

    }
}
