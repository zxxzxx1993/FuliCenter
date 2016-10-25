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
import com.example.administrator.day27project.bean.Result;
import com.example.administrator.day27project.bean.UserAvatar;
import com.example.administrator.day27project.dao.SharePrefrenceUtils;
import com.example.administrator.day27project.dao.UserDao;
import com.example.administrator.day27project.net.NetDao;
import com.example.administrator.day27project.net.OkHttpUtils;
import com.example.administrator.day27project.utils.CommonUtils;
import com.example.administrator.day27project.utils.ImageLoader;
import com.example.administrator.day27project.utils.OnSetAvatarListener;
import com.example.administrator.day27project.utils.ResultUtils;

import java.io.File;

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
    OnSetAvatarListener mOnSetAvatarListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_personself);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        userAvatar = FuLiCenterApplication.getUserAvatar();
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
                finish();
                break;
            case R.id.ueravatar:
                mOnSetAvatarListener = new OnSetAvatarListener(mContext,R.id.lin_peonal,userAvatar.getMuserName(), I.AVATAR_TYPE_USER_PATH);
                break;
            case R.id.tv_nick:
                startActivity(new Intent(this, UpdateNickActivity.class));
                break;
            case R.id.erweima:
                break;
        }
    }

    @OnClick(R.id.btnback)
    public void onClick1() {
        if (ueravatar != null) {
            SharePrefrenceUtils.getInstance(mContext).removeUser();
            FuLiCenterApplication.setUserAvatar(null);
            startActivity(new Intent(mContext, LoginActivity.class));
        }
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvNick.setText(FuLiCenterApplication.getUserAvatar().getMuserNick());
    }

    @OnClick(R.id.setingnick)
    public void onClick() {
        startActivity(new Intent(this, UpdateNickActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
   if (resultCode!=RESULT_OK){
       return;
   }
        mOnSetAvatarListener.setAvatar(requestCode,data,ueravatar);
        if (requestCode==OnSetAvatarListener.REQUEST_CROP_PHOTO){
            updateAvatar();
        }
    }

    private void updateAvatar() {
        File file =new File(OnSetAvatarListener.getAvatarPath(mContext,userAvatar.getMavatarPath()+"/"+userAvatar.getMuserName()+I.AVATAR_SUFFIX_JPG));
        NetDao.updateAvatar(mContext, userAvatar.getMuserName(), file, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, UserAvatar.class);
                Log.e("result","result="+result);
                if (result == null) {
                CommonUtils.showShortToast("更新异常");
            } else {
                if (result.isRetMsg()) {
                    UserAvatar user = (UserAvatar) result.getRetData();
                    UserDao dao = new UserDao(mContext);
                    boolean issuccess = dao.savaUser(user);
                    if (issuccess) {
                        FuLiCenterApplication.setUserAvatar(user);
                        SharePrefrenceUtils.getInstance(mContext).saveUser(user.getMuserName());
                        FuLiCenterApplication.setUserAvatar(user);
                    } else {
                        CommonUtils.showShortToast("数据库操作异常");
                    }
                }
                else {
                    CommonUtils.showShortToast("更新异常");
                }
            }

            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
