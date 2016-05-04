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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gaojun.weather.Adapter.NewsListAdapter;
import com.android.gaojun.weather.GSON.WxhotDao;
import com.android.gaojun.weather.R;
import com.android.gaojun.weather.activity.WebViewActivity;
import com.android.gaojun.weather.model.NewsItem;
import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WxhotFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Context context;

    private EditText mEditText;
    private Button mBtnSearch;
    private TextView wxHotTitle;

    private ListView mListView;
    private NewsListAdapter newsAdapter;

    private List<String> wxhotUrls = new ArrayList<>();
    private Gson gson;
    private List<NewsItem> newsItems;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.arg1) {
                case 1:
                    wxHotTitle.setText(msg.obj.toString());
                    newsAdapter = new NewsListAdapter(context, newsItems);
                    newsAdapter.notifyDataSetChanged();
                    mListView.setAdapter(newsAdapter);
                    break;
            }
            return false;
        }
    });

    public WxhotFragment(Context context) {
        this.context = context;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wxhot, container, false);
        gson = new Gson();
//        mSpinner = (Spinner) findViewById(R.id.spinner_news);
        mListView = (ListView) view.findViewById(R.id.listView_news);
        mBtnSearch = (Button) view.findViewById(R.id.btn_wxhot_search);
        mEditText = (EditText) view.findViewById(R.id.edit_wxhot_search);
        wxHotTitle = (TextView) view.findViewById(R.id.wxhot_text_title);
        getWxhotInfo("20","科比");
        newsItems = new ArrayList<>();

        mBtnSearch.setOnClickListener(this);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(context, wxhotUrls.get(position), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", wxhotUrls.get(position));
                startActivity(intent);
            }
        });
        return view;
    }


    public void getWxhotInfo(String pageNum, final String word) {
        Parameters para = new Parameters();
        para.put("num", pageNum);
        para.put("rand", "1");
        para.put("word", word);
        para.put("page", "2");
        para.put("src", "人民日报");

        ApiStoreSDK.execute("http://apis.baidu.com/txapi/weixin/wxhot", ApiStoreSDK.GET, para, new ApiCallBack() {
            @Override
            public void onSuccess(int i, String s) {
                newsItems.clear();
                wxhotUrls.clear();
                WxhotDao wxhotDao = new WxhotDao();
                wxhotDao = gson.fromJson(s, WxhotDao.class);
                List<WxhotDao.News> news = wxhotDao.getNewslist();
                NewsItem newsItem;
                if (news == null){
                    Toast.makeText(context,"搜不到有关内容",Toast.LENGTH_LONG).show();
                }else {
                    for (int j = 0; j < news.size(); j++) {
                        newsItem = new NewsItem();
                        newsItem.setImageUrl(news.get(j).getPicUrl());
                        newsItem.setTitle(news.get(j).getTitle());
                        newsItems.add(newsItem);
                        wxhotUrls.add(news.get(j).getUrl());
                    }
                }

                Message msg = new Message();
                msg.arg1 = 1;
                msg.obj = word;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(int i, String s, Exception e) {
                super.onError(i, s, e);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_wxhot_search) {
            String text = mEditText.getText().toString();
            getWxhotInfo("20",text);
        }
    }
}
