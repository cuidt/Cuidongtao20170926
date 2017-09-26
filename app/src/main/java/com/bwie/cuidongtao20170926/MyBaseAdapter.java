package com.bwie.cuidongtao20170926;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 崔 on 2017/9/26.
 */

public class MyBaseAdapter extends BaseAdapter {
    private Context context;
    private List<News.DataBean> newsList;


    public MyBaseAdapter(Context context, List<News.DataBean> newsList) {
        this.context = context;
        this.newsList = newsList;
    }


    @Override
    public int getCount() {
        return newsList.size();
    }


    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.list_item, null);
            viewHolder.newsName = (TextView) convertView.findViewById(R.id.newsname);
            viewHolder.newsTime =(ImageView) convertView.findViewById(R.id.newstime);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        //配置数据
        News.DataBean dataBean = newsList.get(position);


        viewHolder.newsName.setText(dataBean.getTITLE().substring(0,5));

        //viewHolder.newsTime.setText(dataBean.getSHOWTIME());
        viewHolder.newsTime.setImageResource(R.mipmap.ic_launcher);

        return convertView;
    }


    class ViewHolder{
        TextView newsName;
        ImageView newsTime;
    }
}
