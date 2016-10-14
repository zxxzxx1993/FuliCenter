package com.example.administrator.day27project.activity;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.day27project.R;
import com.example.administrator.day27project.utils.MFGT;

public class SplashActivity extends AppCompatActivity {
     private  final long Totaltime = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
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
                MFGT.gotoMainActivity(SplashActivity.this);
                MFGT.finish(SplashActivity.this);
            }
        }).start();
    }
}
