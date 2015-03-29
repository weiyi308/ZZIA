package com.rdc.ruan.zzia.Main.Utils;

import android.app.Activity;
import android.os.Build;

import com.rdc.ruan.zzia.Main.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by Ruan on 2015/3/28.
 * lalalalalaa
 */
public class InitStatusBar {
    public static void InitStatusBar(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.actionbar_bg);
        }
    }
    public static void voidvoid(){

    }
}
