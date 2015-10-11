package com.rdc.ruan.zzia.Main.Interface;

import com.android.volley.VolleyError;

/**
 * Created by Ruan on 2015/9/28.
 */
public interface RequestListener {
    public void requestSuccess(Object tag,String result);
    public void requestError(Object tag,VolleyError e);
}
