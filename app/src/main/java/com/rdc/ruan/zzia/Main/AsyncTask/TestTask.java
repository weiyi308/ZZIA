package com.rdc.ruan.zzia.Main.AsyncTask;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.rdc.ruan.zzia.Main.Utils.HttpUtil;
import com.rdc.ruan.zzia.Main.Utils.MyJsoup;
import com.rdc.ruan.zzia.Main.Interface.CallbackListener;

import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by Administrator on 2015/6/22.
 */
public class TestTask extends AsyncTask<String,Void,String> {
    private String url;
    private DefaultHttpClient httpClient;
    private ProgressBar progressBar;
    private CallbackListener callbackListener;
    public TestTask(String url,ProgressBar progressBar) {
        super();
        this.url = url;
        httpClient = new DefaultHttpClient();
        this.progressBar = progressBar;
    }
    public void setCallbackListener(CallbackListener callbackListener){
        this.callbackListener = callbackListener;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressBar.setVisibility(View.GONE);
        callbackListener.Return(s);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        try {
            result = HttpUtil.getUrl(url,
                    httpClient, MyJsoup.getMainUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
