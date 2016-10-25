package com.example.administrator.day27project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.day27project.FuLiCenterApplication;
import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.bean.UserAvatar;
import com.example.administrator.day27project.dao.SharePrefrenceUtils;
import com.example.administrator.day27project.utils.FileUtils;
import com.example.administrator.day27project.utils.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonselfActivity extends AppCompatActivity {

    @Bind(R.id.back2setting)
    ImageView back2setting;
    @Bind(R.id.ueravatar)
    ImageView ueravatar;
    @Bind(R.id.tv_nick)
    TextView tvNick;
    @Bind(R.id.erweima)
    ImageView erweima;
    PersonselfActivity mContext;
    @Bind(R.id.tv_name)
    TextView tvName;
    UserAvatar userAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_personself);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        userAvatar  = FuLiCenterApplication.getUserAvatar();
        if (userAvatar != null) {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(userAvatar), mContext, ueravatar);
            tvNick.setText(userAvatar.getMuserNick());
            tvName.setText(userAvatar.getMuserName());
        } else {
            finish();
        }

    }

    @OnClick({R.id.back2setting, R.id.ueravatar, R.id.tv_nick, R.id.erweima})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back2setting:
                break;
            case R.id.ueravatar:
                break;
            case R.id.tv_nick:
                break;
            case R.id.erweima:
                break;
        }
    }

    @OnClick(R.id.btnback)
    public void onClick() {
      if (ueravatar!=null){
          SharePrefrenceUtils.getInstance(mContext).removeUser();
          FuLiCenterApplication.setUserAvatar(null);
          startActivity(new Intent(mContext,LoginActivity.class));
      }
        finish();
    }
}
