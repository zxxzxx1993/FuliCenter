package com.example.administrator.day27project.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

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
import com.example.administrator.day27project.utils.ResultUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateNickActivity extends AppCompatActivity {

    @Bind(R.id.ed_nick)
    EditText edNick;
    UserAvatar userAvatar;
    UpdateNickActivity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_update_nick);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        userAvatar  = FuLiCenterApplication.getUserAvatar();
        if (userAvatar!=null){
            edNick.setText(userAvatar.getMuserNick());
            edNick.setSelectAllOnFocus(true);
        }else
        {
            finish();
        }
    }

    @OnClick({R.id.updatenick, R.id.btn_update_nick})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.updatenick:
                finish();
                break;
            case R.id.btn_update_nick:
                updateNick();
                break;
        }
    }

    private void updateNick() {
        if (userAvatar!=null){
            String nick  = edNick.getText().toString().trim();
            if (TextUtils.isEmpty(nick)){
                CommonUtils.showShortToast(R.string.nick_name_connot_be_empty);
                return;
            }else if (nick.equals(userAvatar.getMuserNick())){
                CommonUtils.showShortToast("昵称未修改");
                return;
            }
            final ProgressDialog pd = new ProgressDialog(mContext);
            pd.setMessage("更新中");
            pd.show();
            NetDao.updatenick(mContext, userAvatar.getMuserName(), nick, new OkHttpUtils.OnCompleteListener<String>() {
                @Override
                public void onSuccess(String s) {
                    Result result = ResultUtils.getResultFromJson(s, UserAvatar.class);
                    pd.dismiss();
                    if (result == null) {
                        CommonUtils.showShortToast("更新失败");
                    } else {
                        if (result.isRetMsg()) {
                            UserAvatar user = (UserAvatar) result.getRetData();
                            UserDao dao = new UserDao(mContext);
                            boolean issuccess = dao.savaUser(user);
                            if (issuccess) {
                                SharePrefrenceUtils.getInstance(mContext).saveUser(user.getMuserName());
                                FuLiCenterApplication.setUserAvatar(user);
                                finish();
                            } else {
                                CommonUtils.showShortToast("数据库操作异常");
                            }
                        } else {
                            CommonUtils.showShortToast("更新失败");
                        }
                }
                }

                @Override
                public void onError(String error) {
                     CommonUtils.showShortToast(error);
                }
            });
        }

    }
}
