package com.operr.yelpchallenge.common.view;


import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    private static Context context = null;
    private static BaseApplication application = null;


    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();

        application = this;


    }


    public static Context getContext(){
        return context;
    }



    static public BaseApplication getApplication(){
        return application;
    }
}
