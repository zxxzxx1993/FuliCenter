package com.example.administrator.day27project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    @OnClick({R.id.btn_login, R.id.btn_login_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                break;
            case R.id.btn_login_register:
                startActivityForResult(new Intent(this,RegisterActivity.class), I.REQUST_CODE_REGISTER);
//                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==I.REQUST_CODE_REGISTER&&resultCode==RESULT_OK){
            String username = data.getStringExtra(I.User.USER_NAME);
            edUsername.setText(username);
        }
    }
}
