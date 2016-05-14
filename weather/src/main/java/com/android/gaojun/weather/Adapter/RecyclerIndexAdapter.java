package com.android.gaojun.weather.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.gaojun.weather.R;
import com.android.gaojun.weather.model.WeatherIndex;

import java.util.List;


/**
 * Created by Administrator on 2016/5/14.
 */
public class RecyclerIndexAdapter extends RecyclerView.Adapter<RecyclerIndexAdapter.MyViewHolder>{
    private Context context;
    private List<WeatherIndex> weatherIndices;

    public RecyclerIndexAdapter(Context context, List<WeatherIndex> weatherIndices) {
        this.context = context;
        this.weatherIndices = weatherIndices;
    }

    @Override
    public RecyclerIndexAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_index_item,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(weatherIndices.get(position).getName());
        holder.index.setText(weatherIndices.get(position).getIndex());
        holder.details.setText(weatherIndices.get(position).getDetails());
    }

    @Override
    public int getItemCount() {
        return weatherIndices.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView index;
        private TextView details;


        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.index_name);
            index = (TextView) itemView.findViewById(R.id.index_index);
            details = (TextView) itemView.findViewById(R.id.index_details);
        }
    }
}
