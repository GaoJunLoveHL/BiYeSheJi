package com.android.gaojun.weather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.gaojun.weather.R;

import java.util.List;

/**
 * Created by Administrator on 2016/3/22.
 */
public class CityListAdapter extends BaseAdapter {

    private Context context;
    private List<String> cities;

    public CityListAdapter(Context context, List<String> cities) {
        this.context = context;
        this.cities = cities;
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (holder == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_city_name,parent,false);
            holder.tv_cityName = (TextView) convertView.findViewById(R.id.tv_city_name);
            holder.tv_miaoShu = (TextView) convertView.findViewById(R.id.tv_miao_shu);
            holder.iv_location = (ImageView) convertView.findViewById(R.id.iv_location);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_cityName.setText(cities.get(position));
        return convertView;
    }

    public class ViewHolder{
        private TextView tv_cityName;
        private TextView tv_miaoShu;
        private ImageView iv_location;
    }
}
