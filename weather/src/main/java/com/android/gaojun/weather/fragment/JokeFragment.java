package com.android.gaojun.weather.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.gaojun.weather.Adapter.NewsListAdapter;
import com.android.gaojun.weather.GSON.NewsDao;
import com.android.gaojun.weather.GSON.NewsTypesDao;
import com.android.gaojun.weather.R;
import com.android.gaojun.weather.activity.WebViewActivity;
import com.android.gaojun.weather.model.NewsItem;
import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JokeFragment extends Fragment {

    private View view;
    private Context context;
    private Spinner mSpinner;
    private ArrayAdapter mArrayAdapter;
    private NewsListAdapter newsListAdapter;
    private PullToRefreshListView newsList;
    private NewsListAdapter mListAdapter;
    private Gson gson;

    private List<NewsItem> newsItems;
    private List<String> ids;
    private List<String> names;
//    private List<String> titles;
    private List<String> links;
//    private List<String> imageurls;

    private int page = 1;
    private String nowId = null;
//    private String word = "Android";

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.arg1){
                case 1:
                    mArrayAdapter = new ArrayAdapter(context,android.R.layout.simple_list_item_1,names);
                    mArrayAdapter.notifyDataSetChanged();
                    mSpinner.setAdapter(mArrayAdapter);
//                    mSpinner.setPromptId(0);
                    break;
                case 2:
                    newsListAdapter = new NewsListAdapter(context,newsItems);
                    newsListAdapter.notifyDataSetChanged();
                    newsList.setAdapter(newsListAdapter);
                    newsList.getRefreshableView().setSelection((page-1)*20);
                    break;
            }
            return false;
        }
    });

    public JokeFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_joke, container, false);
        gson = new Gson();
        ids = new ArrayList<>();
        names = new ArrayList<>();
//        titles = new ArrayList<>();
        links = new ArrayList<>();
        newsItems = new ArrayList<>();
//        imageurls = new ArrayList<>();
        mSpinner = (Spinner) view.findViewById(R.id.news_types);
        newsList = (PullToRefreshListView) view.findViewById(R.id.listview_joke);

        newsList.setMode(PullToRefreshBase.Mode.PULL_UP_TO_REFRESH);

        getNewsTypes();
//        mSpinner.setAdapter(new ArrayAdapter<String>(context,));
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                page = 1;
                links.clear();
                newsItems.clear();
                nowId = ids.get(position);
                getNews(nowId,page);
                if (newsList.isRefreshing()){
                    newsList.onRefreshComplete();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", links.get(position-1));
                startActivity(intent);
            }
        });

        newsList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getNews(nowId,page);

            }
        });
        return view;
    }

    private void getNewsTypes() {
        final Parameters para = new Parameters();
        ApiStoreSDK.execute("http://apis.baidu.com/showapi_open_bus/channel_news/channel_news",
                ApiStoreSDK.GET, para, new ApiCallBack() {
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onSuccess(int i, String s) {
                        ids.clear();
                        names.clear();
                        NewsTypesDao newsTypesDao = gson.fromJson(s, NewsTypesDao.class);
                        List<NewsTypesDao.ChannelList> channelList= newsTypesDao.getShowapi_res_body().getChannelList();
                        for (int j = 0; j < channelList.size(); j++) {
                            ids.add(channelList.get(j).getChannelId());
                            names.add(channelList.get(j).getName());
                        }
                        getNews(ids.get(0),page);
                        Message msg = new Message();
                        msg.arg1 = 1;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void onError(int i, String s, Exception e) {
                        super.onError(i, s, e);
                    }
                });
    }

    private void getNews(String channelId,int page) {
        Parameters para = new Parameters();

        para.put("page",page+"");
        if (!channelId.equals(null)){
            para.put("channelId",channelId);
        }

        ApiStoreSDK.execute("http://apis.baidu.com/showapi_open_bus/channel_news/search_news",
                ApiStoreSDK.GET,para,new ApiCallBack(){
                    @Override
                    public void onError(int i, String s, Exception e) {
                        super.onError(i, s, e);
                    }

                    @Override
                    public void onSuccess(int i, String s) {
//                        links.clear();
//                        newsItems.clear();
                        NewsDao newsDao = gson.fromJson(s, NewsDao.class);
                        List<NewsDao.ContentList> contentList = newsDao.getShowapi_res_body().getPagebean().getContentlist();
                        int size = contentList.size();
                        NewsItem newsItem;
                        if (contentList.size()==0){
                            Toast.makeText(context,"到底了",Toast.LENGTH_SHORT).show();
                        }else {
                            for (int j = 0; j < size; j++) {
                                newsItem = new NewsItem();
                                links.add(contentList.get(j).getLink());
                                newsItem.setTitle(contentList.get(j).getTitle());
                                if (contentList.get(j).getImageurls().size()>0){
                                    newsItem.setImageUrl(contentList.get(j).getImageurls().get(0).getUrl());
                                }else {
                                    newsItem.setImageUrl("https://cdn0.iconfinder.com/data/icons/android-icons/512/error-01-128.png");
                                }
                                newsItems.add(newsItem);
                            }
                        }

                        Message msg = new Message();
                        msg.arg1 = 2;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void onComplete() {
                        if (newsList.isRefreshing()){
                            newsList.onRefreshComplete();
                        }
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
