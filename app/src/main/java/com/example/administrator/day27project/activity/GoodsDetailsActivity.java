package com.example.administrator.day27project.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;

public class GoodsDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        int goodsId = getIntent().getIntExtra(I.Goods.KEY_GOODS_ID,0);
        Log.e("zxxzxx",goodsId+"");
    }
}
