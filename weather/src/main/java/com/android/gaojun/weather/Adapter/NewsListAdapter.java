package com.android.gaojun.weather.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.gaojun.weather.R;
import com.android.gaojun.weather.model.NewsItem;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.List;

/**
 * Created by Administrator on 2016/4/14.
 */
public class NewsListAdapter extends BaseAdapter {
    private Context context;
    private List<NewsItem> newsItems;
    private ImageLoader imageLoader;

    public NewsListAdapter(Context context, List<NewsItem> newsItems) {
        this.context = context;
        this.newsItems = newsItems;
        RequestQueue mQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(mQueue, new BitmapCache());
    }

    @Override
    public int getCount() {
        return newsItems.size();
    }

    @Override
    public Object getItem(int position) {
        return newsItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (holder == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_news_item, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.list_news_title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.list_news_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String url = newsItems.get(position).getImageUrl();
        holder.title.setText(newsItems.get(position).getTitle());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.imageView,
                R.mipmap.loading,R.mipmap.load_error);
        imageLoader.get(url,listener);
        return convertView;
    }

    public class ViewHolder {
        private TextView title;
        private ImageView imageView;
    }


    public class BitmapCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }
    }
}
