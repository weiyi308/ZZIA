package com.rdc.ruan.zzia.Main.AsyncTask;

import android.os.AsyncTask;
import android.os.Bundle;

import com.rdc.ruan.zzia.Main.Utils.HttpUtil;
import com.rdc.ruan.zzia.Main.Interface.CallbackListener;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

/**
 * Created by Ruan on 2015/6/25.
 */
public class CxffTask extends AsyncTask<String,Void,String> {
    private String url;
    private String userID;
    private String password;
    private String code;
    private Bundle args;
    private DefaultHttpClient httpClient;
    private CallbackListener callbackListener;
    private List<BasicNameValuePair> list;
    public CxffTask(String url,List<BasicNameValuePair> list) {
        super();
        this.url = url;
        this.list = list;
        httpClient = new DefaultHttpClient();
    }
    public void setCallbackListener(CallbackListener callbackListener){
        this.callbackListener = callbackListener;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        callbackListener.Return(s);
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        try {
            result = HttpUtil.postUrl(url,
                    list,
                    httpClient,
                    url);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
