package com.example.administrator.day27project.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.day27project.FuLiCenterApplication;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.bean.UserAvatar;
import com.example.administrator.day27project.dao.SharePrefrenceUtils;
import com.example.administrator.day27project.dao.UserDao;
import com.example.administrator.day27project.utils.CommonUtils;
import com.example.administrator.day27project.utils.MFGT;

public class SplashActivity extends AppCompatActivity {
     private  final long Totaltime = 2000;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             long startTime =  SystemClock.currentThreadTimeMillis();
               long time = SystemClock.currentThreadTimeMillis();
                long costTime = time-startTime;
                if (Totaltime-costTime>0){
                    try {
                        Thread.sleep(Totaltime-costTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                UserAvatar userAvatar = FuLiCenterApplication.getUserAvatar();
                String username = SharePrefrenceUtils.getInstance(context).getUser();
                Log.e("卧槽","username="+username);
                if (userAvatar==null&&username!=null){
                    UserDao dao = new UserDao(context);
                    userAvatar = dao.getUser(username);
                    Log.e("卧槽","user="+userAvatar);
                    if (userAvatar!=null){
                        FuLiCenterApplication.setUserAvatar(userAvatar);
                    }
                }
                MFGT.gotoMainActivity(SplashActivity.this);
                finish();
            }
        },Totaltime);
    }
}
