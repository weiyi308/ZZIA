package com.rdc.ruan.zzia.Main.Utils.Http;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.rdc.ruan.zzia.Main.Activity.LoginActivity;
import com.rdc.ruan.zzia.Main.Activity.MainFragmentActivity;
import com.rdc.ruan.zzia.Main.Interface.RequestListener;
import com.rdc.ruan.zzia.Main.MyApp;
import com.rdc.ruan.zzia.Main.Utils.Constant;
import com.rdc.ruan.zzia.Main.Utils.Tools;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Ruan on 2015/9/28.
 */
public class RequestManager {
    private RequestQueue requestQueue;
    private Context context;
    private int count = 0;
    private ProgressBar progressBar;

    public RequestManager(Context context){
        this.context = context;
        requestQueue = Volley.newRequestQueue(MyApp.getInstance());
    }
    public void post(String url,Object tag,Object params,
                      RequestListener listener){
        post(url,tag,params,listener,null,null);
    }

    public void post(String url,Object tag,Object params,RequestListener listener,
                     Map<String, String> mHeaders,ProgressBar progressBar){
        ByteArrayRequest byteArrayRequest = new ByteArrayRequest(Request.Method.POST,
                url,params,responseListener(tag,listener),
                errorListener(tag,listener));
        if (mHeaders!=null){
            byteArrayRequest.setmHeaders(mHeaders);
        }
        this.progressBar = progressBar;
        byteArrayRequest.setShouldCache(true);
        byteArrayRequest.setCacheTime(12*60*1000);
        addRequest(byteArrayRequest,tag);
    }


    public void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        if (progressBar!=null){
            progressBar.setVisibility(View.VISIBLE);
        }
        count++;
        requestQueue.add(request);
    }

    public void cancelAll(Context context) {
        count = 0;
        requestQueue.cancelAll(context);
    }

    /**
     * 访问成功
     * @return
     */
    protected Response.Listener<byte[]> responseListener(final Object tag,final RequestListener rl){
        return new Response.Listener<byte[]>() {
            @Override
            public void onResponse(byte[] bytes) {
                if (progressBar!=null){
                    progressBar.setVisibility(View.GONE);
                }
                count--;
                String encode = "";
                try {
                    encode = Tools.changeCharSet(bytes, "gb2312", "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (!tag.equals(Constant.TAG_LOGIN)&&
                        encode.contains("欢迎使用正方教务管理系统！请登录")){
                    Toast.makeText(context,"请重新登录",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    if (MainFragmentActivity.mainFragmentActivity!=null){
                        MainFragmentActivity.mainFragmentActivity.finish();
                    }
                    return;
                }
                rl.requestSuccess(tag,encode);
                Log.e("success:"+tag.toString(),encode);
            }
        };
    }

    /**
     * 访问失败
     * @return
     */
    protected Response.ErrorListener errorListener(final Object tag, final RequestListener rl){
        return  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (progressBar!=null){
                    progressBar.setVisibility(View.GONE);
                }
                count--;
                rl.requestError(tag,volleyError);
                Log.e("err:"+tag.toString(),volleyError.getMessage());
            }
        };
    }
}
