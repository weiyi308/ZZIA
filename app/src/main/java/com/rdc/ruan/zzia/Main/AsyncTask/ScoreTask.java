package com.rdc.ruan.zzia.Main.AsyncTask;

import android.os.AsyncTask;

import com.rdc.ruan.zzia.Main.HttpUtils.HttpUtil;
import com.rdc.ruan.zzia.Main.Interface.CallbackListener;

import org.apache.http.impl.client.DefaultHttpClient;


/**
 * Created by Ruan on 2015/3/13.
 */
public class ScoreTask extends AsyncTask {
    CallbackListener callbackListener;
    String url,userid;
    public void setCallbackListener(CallbackListener callbackListener){
        this.callbackListener=callbackListener;
    }
    public ScoreTask(String url, String userid) {
        super();
        this.url=url;
        this.userid=userid;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        DefaultHttpClient client =new DefaultHttpClient();
        String string="";
        try {
            string= HttpUtil.getUrl(url, client, HttpUtil.GetCookieUrl(url) + "xs_main.aspx?xh=" + userid);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        callbackListener.Return(o);
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }
}
