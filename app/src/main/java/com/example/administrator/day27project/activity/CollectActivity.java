package com.example.administrator.day27project.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.day27project.FuLiCenterApplication;
import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.adapter.CollectAdapter;
import com.example.administrator.day27project.adapter.GoodsAdapter;
import com.example.administrator.day27project.bean.CollectBean;
import com.example.administrator.day27project.bean.NewGoodsBean;
import com.example.administrator.day27project.bean.UserAvatar;
import com.example.administrator.day27project.fragment.CollectFragment;
import com.example.administrator.day27project.net.NetDao;
import com.example.administrator.day27project.net.OkHttpUtils;
import com.example.administrator.day27project.utils.CommonUtils;
import com.example.administrator.day27project.utils.ConvertUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollectActivity extends AppCompatActivity {

    CollectAdapter mGoodsAdapter;
    ArrayList<CollectBean> mlist;

    CollectActivity mContext;
    CollectFragment mCategoryFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        toFinash();
        mContext = this;
        mlist = new ArrayList<>();
        mCategoryFragment = new CollectFragment();
        mGoodsAdapter = new CollectAdapter(mContext,mlist);
  initFragment();

    }



    private void toFinash() {
        UserAvatar userAvatar = FuLiCenterApplication.getUserAvatar();
        if (userAvatar==null){
            finish();
        }

    }

    @OnClick(R.id.collect_back)
    public void onClick() {
        finish();
    }
    private void initFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_collect, mCategoryFragment)
                .show(mCategoryFragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FuLiCenterApplication.getInstance().setTime(1);
    }
}
