package com.bwie.cuidongtao20170926;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PullToRefreshListView listView;
    private static final String JSON_URL = "http://www.93.gov.cn/93app/data.do?channelId=2&startNum=";
    private ArrayList<News> newsList;
    private MyBaseAdapter myAdatper;
    private List<News.DataBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  
        //获取资源ID
       listView=(PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        //初始化组件
        initView();
        //初始化加载数据
        initData();

    }

    public void initData(){
        pullToRefreshData();
    }

    //下载刷新，加载数据，显示数据
    public void pullToRefreshData(){
        new AsyncTask<String,Integer,String>(){
            @Override
            protected String doInBackground(String... params) {


                String json = new NetWorkUtil().getJsonByHttpUrlConnection(JSON_URL+"1");


                return json;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //解析json
                Gson gson =new Gson();
                News news = gson.fromJson(s, News.class);
                data = news.getData();
                myAdatper = new MyBaseAdapter(MainActivity.this, data);
                listView.setAdapter(myAdatper);
                listView.onRefreshComplete();//停止刷新
            }
        }.execute();
    }
    int index = 1;
    //下载刷新，加载数据，显示数据
    public void loadMoreData(){
        new AsyncTask<String,Integer,String>(){
            @Override
            protected String doInBackground(String... params) {
                index++;
                String json = new NetWorkUtil().getJsonByHttpUrlConnection(JSON_URL+index);
                return json;
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //解析json
             /*   SuperClass superClass = new Gson().fromJson(s, SuperClass.class);
                newsList.addAll(superClass.getData());*/
                Gson gson =new Gson();
                News news = gson.fromJson(s, News.class);
                data.addAll(news.getData());
                myAdatper.notifyDataSetChanged();
            }
        }.execute();
    }


    public void initView(){
        listView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //当下拉刷新，进入此方法
                Toast.makeText(MainActivity.this,"下拉刷新",Toast.LENGTH_SHORT).show();
                //加载新数据，刷新显示
                pullToRefreshData();


            }
        });
        listView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                Toast.makeText(MainActivity.this,"上拉显示出listview的最后一行",Toast.LENGTH_SHORT).show();
                loadMoreData();
            }
        });
    }
}
