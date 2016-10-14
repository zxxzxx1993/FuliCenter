package com.example.administrator.day27project.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.day27project.R;
import com.example.administrator.day27project.utils.L;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.i(" 哈哈哈哈 ");
        setContentView(R.layout.activity_main);

    }
    public  void onCheckedChange(View view){

    }
}
