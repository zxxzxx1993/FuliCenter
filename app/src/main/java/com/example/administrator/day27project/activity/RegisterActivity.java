package com.example.administrator.day27project.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.day27project.R;
import com.example.administrator.day27project.bean.Result;
import com.example.administrator.day27project.net.NetDao;
import com.example.administrator.day27project.net.OkHttpUtils;
import com.example.administrator.day27project.utils.CommonUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.ed_register_username)
    EditText edRegisterUsername;
    @Bind(R.id.ed_nick)
    EditText edNick;
    @Bind(R.id.ed_register_password)
    EditText edRegisterPassword;
    @Bind(R.id.ed_register_confirmpassword)
    EditText edRegisterConfirmpassword;
    @Bind(R.id.iv_register_back)
    ImageView ivRegisterBack;
    @Bind(R.id.btn_register)
    Button btnRegister;
    String username;
    String nick;
    String password;
    RegisterActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_register);
        context = this;
        ButterKnife.bind(this);

        edRegisterConfirmpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        super.onCreate(savedInstanceState);
    }

    private void toregister() {
        NetDao.register(context, username, nick, password, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                if (result == null) {
                    CommonUtils.showShortToast(R.string.register_fail);
                } else {
                    if (result.isRetMsg()) {
                        CommonUtils.showShortToast(R.string.register_success);
                        finish();
                    } else {
                        CommonUtils.showShortToast(R.string.register_fail_exists);
                        edRegisterUsername.requestFocus();
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @OnClick(R.id.btn_register)
    public void register() {
        username = edRegisterUsername.getText().toString().trim();
        nick = edNick.getText().toString().trim();
        password = edRegisterPassword.getText().toString().trim();
        String cofirmpassword = edRegisterConfirmpassword.getText().toString().trim();
        if (username.equals("")) {
            CommonUtils.showShortToast(R.string.user_name_connot_be_empty);
            edRegisterUsername.requestFocus();
            return;
        } else if (!username.matches("[a-zA-Z]\\w{5,15}")) {
            CommonUtils.showShortToast(R.string.illegal_user_name);
            edRegisterUsername.requestFocus();
            return;
        } else if (nick.equals("")) {
            CommonUtils.showShortToast(R.string.nick_name_connot_be_empty);
            edNick.requestFocus();
            return;
        } else if (password.equals("")) {
            CommonUtils.showShortToast(R.string.password_connot_be_empty);
            edRegisterPassword.requestFocus();
            return;
        }
        else if (cofirmpassword.equals("")) {
            CommonUtils.showShortToast(R.string.confirm_password_connot_be_empty);
            edRegisterConfirmpassword.requestFocus();
            return;
        } else if (!password.equals(cofirmpassword)) {
            CommonUtils.showShortToast(R.string.two_input_password);
            edRegisterConfirmpassword.requestFocus();
            return;
        }
        toregister();
    }

    @OnClick(R.id.iv_register_back)
    public void onClick() {
        finish();
    }

}
