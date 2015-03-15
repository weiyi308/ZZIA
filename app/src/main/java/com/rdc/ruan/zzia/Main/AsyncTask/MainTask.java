package com.rdc.ruan.zzia.Main.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.rdc.ruan.zzia.Main.HttpUtils.HttpUtil;
import com.rdc.ruan.zzia.Main.Interface.CallbackListener;

import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Ruan on 2015/3/13.
 */
public class MainTask extends AsyncTask {
    String string;
    CallbackListener callbackListener;
    public MainTask(String string) {
        super();
        this.string = string;
    }
    public void setCallbackListener(CallbackListener callbackListener){
        this.callbackListener = callbackListener;
    }
    @Override
    protected Object doInBackground(Object[] params) {
        DefaultHttpClient client =new DefaultHttpClient();
        String result="";
        String str = HttpUtil.GetCookieUrl(string);
        String header =str+"xs_main.aspx?xh=121006222";
        str += "xskbcx.aspx?xh=121006222&xm=阮东川&gnmkdm=N121603";
        Log.i("Log", str);
        try {
             result= HttpUtil.getUrl(str,client,header);
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.i("Log", result);
        return  result;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        String result = (String)o;
        callbackListener.Return(o);
        System.out.println(result);
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }
}
