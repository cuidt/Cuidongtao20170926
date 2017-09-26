package com.bwie.cuidongtao20170926;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 崔 on 2017/9/26.
 */

public class NetWorkUtil {
    public String getJsonByHttpUrlConnection(String jsonUrl){
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        String jsonStr = "";
        try {
            url = new URL(jsonUrl);//创建URL对象，连接JSON_URL对应的服务器
            httpURLConnection = (HttpURLConnection) url.openConnection();//打开连接
            httpURLConnection.setConnectTimeout(5000);//设置请求时长为5秒
            httpURLConnection.setReadTimeout(5000);//设置读取服务器响应数据的时长


            int resCode = httpURLConnection.getResponseCode();//得到响应结果码


            if(resCode == 200){//表示响应成功，可以获得相应的额结果
                //得到响应的结果,,服务器响应的输入流
                InputStream inputStream = httpURLConnection.getInputStream();
                byte[] buffers = new byte[1024];
                int length = 0;
                while((length = inputStream.read(buffers)) != -1){//本次循环成立，说明读取到了数据
                    jsonStr += new String(buffers,0,length);
                }
                //循环完毕，读取数据完毕
                Log.d("main",jsonStr);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

}
