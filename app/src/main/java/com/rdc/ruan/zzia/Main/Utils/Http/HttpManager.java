package com.rdc.ruan.zzia.Main.Utils.Http;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rdc.ruan.zzia.Main.Interface.RequestListener;
import com.rdc.ruan.zzia.Main.MyApp;
import com.rdc.ruan.zzia.Main.Utils.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ruan on 2015/9/28.
 */
public class HttpManager {
    private RequestManager requestManager;
    private RequestParams params;
    private RequestListener listener;
    private Context context;
    private Map<String,String> map;
    private Map<String,String> headers;
    private ProgressBar progressBar;

    public HttpManager(Context context, RequestListener listener){
        this.context = context;
        requestManager = new RequestManager(context);
        params = new RequestParams();
        headers = new HashMap<String, String>();
        map = new HashMap<String, String>();
        this.listener = listener;
    }


    public void login(String username,String password,String code){
        headers.put("Referer",MyApp.getZziaUrlWithcookie());
        params.put("txtUserName",username);
        params.put("TextBox2",password);
        params.put("__VIEWSTATE", Constant.VIEW_STATE);
        params.put("__VIEWSTATEGENERATOR","92719903");
        params.put("RadioButtonList1","%D1%A7%C9%FA");
        params.put("txtSecretCode",code);
        params.put("Button1","");
        params.put("lbLanguage","");
        params.put("hidPdrs","");
        params.put("hidsc","");
        Log.e("url=",MyApp.getZziaUrlWithcookie());
        doPost(MyApp.getZziaUrlWithcookie(), Constant.TAG_LOGIN);
    }

    public void getInfo(String url,String Referer){
        headers.put("Referer",Referer);
        doPost(url, Constant.TAG_DETAIL);
    }

    public void getScore(String Referer,ProgressBar progressBar){
        headers.put("Referer",Referer);
        this.progressBar = progressBar;
        doPost(MyJsoup.getScoreUrl(),Constant.TAG_SCORE);

    }
    /*public void postScore(String year,String term,ProgressBar progressBar){
        headers.put("Referer",MyJsoup.getMainUrl());
        params.put("__EVENTTARGET","");
        params.put("__EVENTARGUMENT","");
        params.put("btnCx","+%3f%3f++%d1%af+");
        params.put("ddlxq",term);
        params.put("ddlxn",year);
        params.put("__VIEWSTATE",MyJsoup.SCORE_VIEWSTATE);
        params.put("__VIEWSTATEGENERATOR", "EC2DE6FD");
        Log.e("year=",term+year);
        Log.e("url=",MyJsoup.getScoreUrl());
        this.progressBar = progressBar;
        doPost(MyJsoup.getScoreUrl(), Constant.TAG_POST_SCORE);
    }*/
    public void postScore(String year,String term,ProgressBar progressBar){
        headers.put("Referer",MyJsoup.getScoreUrl());
        params.put("txtQSCJ","0");
        params.put("txtZZCJ","100");
        params.put("Button2","%D4%DA%D0%A3%D1%A7%CF%B0%B3%C9%BC%A8%B2%E9%D1%AF");
        params.put("ddlXQ",term);
        params.put("ddlXN",year);
        params.put("__VIEWSTATE",MyJsoup.SCORE_VIEWSTATE);
        params.put("__VIEWSTATEGENERATOR", "8963BEEC");
        Log.e("year=",term+year);
        Log.e("url=",MyJsoup.getScoreUrl());
        this.progressBar = progressBar;
        doPost(MyJsoup.getScoreUrl(), Constant.TAG_POST_SCORE);
    }

    public void getCet(){
        headers.put("Referer",MyJsoup.getMainUrl());
        doPost(MyJsoup.getCetUrl(),Constant.TAG_CET);
    }

    public void getClasstable(ProgressBar progressBar){
        this.progressBar = progressBar;
        headers.put("Referer",MyJsoup.getMainUrl());
        doPost(MyJsoup.getClassTableUrl(),Constant.TAG_GET_CLASSTABLE);
    }

    public void postClasstable(String year,String term,ProgressBar progressBar){
        headers.put("Referer",MyJsoup.getMainUrl());
        params.put("ddlXN",year);
        params.put("ddlXQ",term);
        params.put("__VIEWSTATE",Constant.CLASSTABLE_VIEW_STATE);
        params.put("__EVENTTARGET","ddlXQ");
        params.put("__VIEWSTATEGENERATOR","FDD5582C");
        params.put("__EVENTARGUMENT","");
        this.progressBar = progressBar;
        doPost(MyJsoup.getClassTableUrl(),Constant.TAG_POST_CLASSTABLE);
    }

    private void doPost(String url,Object tag){
        if (!NetUtils.isNetworkAvailable(context)){
            Toast.makeText(context,"网络不可用",Toast.LENGTH_SHORT).show();
            return;
        }
        /*if (!headers.isEmpty()){
            requestManager.post(url,tag,params,listener,headers,progressBar);
            Log.e("hasheader=","true");
        }else {
            requestManager.post(url, tag, params, listener, null,progressBar);
            Log.e("hasheader=","false");
        }*/
        requestManager.post(url, tag, params, listener, headers,progressBar);
        headers = new HashMap<String, String>();
        params = new RequestParams();
    }
}
