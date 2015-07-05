package com.rdc.ruan.zzia.Main.AsyncTask;

import android.os.AsyncTask;

import com.rdc.ruan.zzia.Main.HttpUtils.HttpUtil;
import com.rdc.ruan.zzia.Main.HttpUtils.MyJsoup;
import com.rdc.ruan.zzia.Main.Interface.CallbackListener;

import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by Ruan on 2015/6/30.
 */
public class ClassTableGetTask extends AsyncTask<String,Void,String> {
    private String url;
    private DefaultHttpClient httpClient;
    private CallbackListener callbackListener;
    public ClassTableGetTask(String url) {
        super();
        this.url = url;
        httpClient = new DefaultHttpClient();
    }
    public void setCallbackListener(CallbackListener callbackListener){
        this.callbackListener = callbackListener;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        callbackListener.Return(s);
    }

    @Override
    protected String doInBackground(String... params){
        String result = "";
        try {
            result = HttpUtil.getUrl(url,httpClient, MyJsoup.getMainUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
