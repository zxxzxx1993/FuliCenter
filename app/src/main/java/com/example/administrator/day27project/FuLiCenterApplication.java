package com.example.administrator.day27project;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/10/17.
 */
public class FuLiCenterApplication extends Application{
    public static FuLiCenterApplication application;
    private  static FuLiCenterApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        instance = this;
    }

    public  static FuLiCenterApplication getInstance(){
        if (instance==null){
            instance = new FuLiCenterApplication();
        }
        return instance;
    }
}
