package com.android.gaojun.weather.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.gaojun.weather.R;
import com.android.gaojun.weather.model.HistoryWeather;

import java.util.List;

/**
 * Created by Administrator on 2016/5/9.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private Context context;
    private List<HistoryWeather> weathers;

    public RecyclerAdapter(Context context,List<HistoryWeather> weathers) {
        this.context = context;
        this.weathers = weathers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item,
                parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_week.setText(weathers.get(position).getWeek());
        holder.tv_htemp.setText(weathers.get(position).getHightemp());
        holder.tv_ltemp.setText(weathers.get(position).getLowtemp());
        holder.tv_weather.setText(weathers.get(position).getType());
        holder.tv_wind.setText(weathers.get(position).getFengxiang());
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_week;
        private TextView tv_htemp;
        private TextView tv_ltemp;
        private TextView tv_weather;
        private TextView tv_wind;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_week = (TextView) itemView.findViewById(R.id.tv_week);
            tv_htemp = (TextView) itemView.findViewById(R.id.tv_htemp);
            tv_ltemp = (TextView) itemView.findViewById(R.id.tv_ltemp);
            tv_weather = (TextView) itemView.findViewById(R.id.tv_type);
            tv_wind = (TextView) itemView.findViewById(R.id.tv_wind);
        }
    }
}
