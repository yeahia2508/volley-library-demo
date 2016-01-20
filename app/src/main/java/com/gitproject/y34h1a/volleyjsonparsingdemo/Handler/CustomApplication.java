package com.gitproject.y34h1a.volleyjsonparsingdemo.Handler;

import android.app.Application;
import android.content.Context;

/**
 * Created by Y34H1A on 6/23/2015.
 */
public class CustomApplication extends Application {
    public static Application sInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
    public static Application getInstance() {
        return sInstance;
    }
    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
