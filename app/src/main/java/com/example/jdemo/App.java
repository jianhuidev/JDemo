package com.example.jdemo;

import android.app.Application;
import android.content.Context;

/**
 * 计划用MVP + RxJava + Retrofit
 */
public class App extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    /**
     * @return
     * 全局的上下文
     */
    public static Context getAppContext() {
        return appContext;
    }

}
