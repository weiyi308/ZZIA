package com.rdc.ruan.zzia.Main;

import android.app.Application;

/**
 * Created by Ruan on 2015/9/28.
 */
public class MyApp extends Application {
    private static MyApp instance;
    private static String ZZIA_URL_WITHCOOKIE;
    private static String user,password;

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        MyApp.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        MyApp.password = password;
    }

    public static String getZziaUrlWithcookie() {
        return ZZIA_URL_WITHCOOKIE;
    }

    public static void setZziaUrlWithcookie(String zziaUrlWithcookie) {
        ZZIA_URL_WITHCOOKIE = zziaUrlWithcookie;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    public synchronized static MyApp getInstance(){
        return instance;
    }
}
