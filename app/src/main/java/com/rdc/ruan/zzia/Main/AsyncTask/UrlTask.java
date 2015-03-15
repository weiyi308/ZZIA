package com.rdc.ruan.zzia.Main.AsyncTask;

import android.os.AsyncTask;

import com.rdc.ruan.zzia.Main.HttpUtils.HttpUtil;
import com.rdc.ruan.zzia.Main.Interface.CallbackListener;


/**
 * Created by Ruan on 2015/3/13.
 */
public class UrlTask extends AsyncTask {
    private String string;
    private CallbackListener callbackListener;
    public UrlTask(String string) {
        super();
        this.string=string;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        String url="";
        try {
            url = HttpUtil.GetRealUrl(string);
        }catch (Exception e){
            e.printStackTrace();
        }

        return url;
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
    public void setCallbackListener(CallbackListener callbackListener){
        this.callbackListener = callbackListener;
    }
}
