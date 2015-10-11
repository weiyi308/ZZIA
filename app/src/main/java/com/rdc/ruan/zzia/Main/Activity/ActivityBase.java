package com.rdc.ruan.zzia.Main.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.rdc.ruan.zzia.Main.Interface.RequestListener;
import com.rdc.ruan.zzia.Main.Utils.Http.HttpManager;

public abstract class ActivityBase extends AppCompatActivity {
    protected HttpManager httpManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        httpManager = new HttpManager(this,requestListener());
        //init();
    }

    @Override
    public void setContentView(int layoutResID) {
        // TODO Auto-generated method stub
        super.setContentView(layoutResID);
        init();
    }
    @Override
    public void setContentView(View view) {
        // TODO Auto-generated method stub
        super.setContentView(view);

    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        // TODO Auto-generated method stub
        super.setContentView(view, params);
    }



    @Override
    protected void onStop() {
        super.onStop();

    }
    protected RequestListener requestListener(){
        return new RequestListener() {
            @Override
            public void requestSuccess(Object tag, String result) {

            }

            @Override
            public void requestError(Object tag, VolleyError e) {

            }
        };
    }
    private void init(){
        initView();
        initData();
    }
    public abstract void initView();
    public abstract void initData();
}
