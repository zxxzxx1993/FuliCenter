package com.example.administrator.day27project.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.iv_login_back)
    ImageView ivLoginBack;
    @Bind(R.id.ed_username)
    EditText edUsername;
    @Bind(R.id.ed_password)
    EditText edPassword;
    String name;
    String password;
    LoginActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        ButterKnife.bind(this);
        edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    @OnClick({R.id.btn_login, R.id.btn_login_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                checkInput();
                break;
            case R.id.btn_login_register:
                startActivityForResult(new Intent(this, RegisterActivity.class), I.REQUST_CODE_REGISTER);
//                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }

    private void checkInput() {
        name = edUsername.getText().toString().trim();
        password = edPassword.getText().toString().trim();
        if (name.equals("")) {
            CommonUtils.showShortToast(R.string.user_name_connot_be_empty);
            edUsername.requestFocus();
            return;
        } else if (password.equals("")) {
            CommonUtils.showShortToast(R.string.password_connot_be_empty);
            edPassword.requestFocus();
            return;
        }
        login();
    }

    private void login() {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("登录中");
        pd.show();
        NetDao.login(context, name, password, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, UserAvatar.class);
                pd.dismiss();
                if (result == null) {
                    CommonUtils.showShortToast(R.string.login_fail);
                } else {
                    if (result.isRetMsg()) {
                        UserAvatar user = (UserAvatar) result.getRetData();
                        UserDao dao = new UserDao(context);
                        boolean issuccess = dao.savaUser(user);
                        if (issuccess) {
                            SharePrefrenceUtils.getInstance(context).saveUser(user.getMuserName());
                            FuLiCenterApplication.setUserAvatar(user);
                            finish();
                        } else {
                            CommonUtils.showShortToast("数据库操作异常");
                        }
                    } else if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                        CommonUtils.showShortToast(R.string.login_unknowusername);
                    } else if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                        CommonUtils.showShortToast(R.string.login_passwod_error);
                    } else {
                        CommonUtils.showShortToast(R.string.login_fail);
                    }
                }
            }

            @Override
            public void onError(String error) {
                CommonUtils.showShortToast(error);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == I.REQUST_CODE_REGISTER && resultCode == RESULT_OK) {
            String username = data.getStringExtra(I.User.USER_NAME);
            edUsername.setText(username);
        }
    }

    @OnClick(R.id.iv_login_back)
    public void onClick() {
        finish();
    }
}
