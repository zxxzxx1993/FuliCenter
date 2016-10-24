package com.example.administrator.day27project;

import android.app.Application;
import android.content.Context;

import com.example.administrator.day27project.bean.UserAvatar;

/**
 * Created by Administrator on 2016/10/17.
 */
public class FuLiCenterApplication extends Application{
    public static FuLiCenterApplication application;
    private  static FuLiCenterApplication instance;
    public static UserAvatar userAvatar;

    public static UserAvatar getUserAvatar() {
        return userAvatar;
    }

    public static void setUserAvatar(UserAvatar userAvatar) {
        FuLiCenterApplication.userAvatar = userAvatar;
    }

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
