package com.rdc.ruan.zzia.Main.Utils;

import android.app.Activity;
import android.os.Build;
import android.view.Window;

import com.meizu.flyme.reflect.StatusBarProxy;
import com.rdc.ruan.zzia.Main.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by Ruan on 2015/3/28.
 * lalalalalaa
 */
public class InitStatusBar {
    public static void InitStatusBar(Activity activity,Window window,boolean dark){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.actionbar_bg);
            StatusBarProxy.setStatusBarDarkIcon(window,dark);
        }
    }
}
