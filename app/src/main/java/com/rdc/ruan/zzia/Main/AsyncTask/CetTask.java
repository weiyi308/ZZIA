package com.rdc.ruan.zzia.Main.AsyncTask;

import android.os.AsyncTask;
import android.widget.TextView;

import com.rdc.ruan.zzia.Main.HttpUtils.HttpUtil;
import com.rdc.ruan.zzia.Main.HttpUtils.MyJsoup;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.net.URLEncoder;

/**
 * Created by Ruan on 2015/3/17.
 */
public class CetTask extends AsyncTask {
    String cetid,name;
    TextView textView;
    public CetTask(String cetid,String name,TextView textView) {
        super();
        this.cetid=cetid;
        this.name=name;
        this.textView=textView;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        DefaultHttpClient client =new DefaultHttpClient();
        String result="";
        try {
            String url=new String("http://www.chsi.com.cn/cet/query?zkzh="+
                    cetid+
                    "&xm="
                    + URLEncoder.encode(name, HTTP.UTF_8));
            result= HttpUtil.getUrl(url,client,"http://www.chsi.com.cn/cet/");
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        String result=(String)o;
        System.out.println(result);
        textView.setText(MyJsoup.getCetTable(result));
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }
}
