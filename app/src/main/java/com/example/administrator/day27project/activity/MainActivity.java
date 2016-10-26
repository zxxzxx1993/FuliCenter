package com.example.administrator.day27project.activity;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.example.administrator.day27project.FuLiCenterApplication;
import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.adapter.CategoryAdapter;
import com.example.administrator.day27project.fragment.BoutiqueFragment;
import com.example.administrator.day27project.fragment.CotegoryFragment;
import com.example.administrator.day27project.fragment.NewGoodsFragment;
import com.example.administrator.day27project.fragment.PersonFragment;
import com.example.administrator.day27project.utils.L;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    @Bind(R.id.layout_boutique)
    RadioButton  boutique;
    @Bind(R.id.layout_category)
    RadioButton category;
    @Bind(R.id.layout_new_goods)
    RadioButton newGoods;
    @Bind(R.id.layout_personal_center)
    RadioButton personalCenter;
    @Bind(R.id.layout_cart)
    RadioButton cart;
    RadioButton [] btns;
    int index=0;
    int currentindex=0;
    int time = 0;
    Fragment[] mFrtagments;
    NewGoodsFragment mFragment;
    BoutiqueFragment mBoutiqueFragment;
    CotegoryFragment mCotegoryFragment;
    PersonFragment  mPersonFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initView();
        initFragment();
    }

    private void initFragment() {
           mFrtagments = new Fragment[5];
           mFragment = new NewGoodsFragment();
          mBoutiqueFragment = new BoutiqueFragment();
         mCotegoryFragment = new CotegoryFragment();
        mPersonFragment = new PersonFragment();
        mFrtagments[0] = mFragment;
        mFrtagments[1] = mBoutiqueFragment;
        mFrtagments[2] = mCotegoryFragment;
        mFrtagments[4] = mPersonFragment;
          getSupportFragmentManager()
                  .beginTransaction()
                 .add(R.id.fragment_container,mFragment)
                  .add(R.id.fragment_container,mBoutiqueFragment)
                  .add(R.id.fragment_container,mCotegoryFragment)
                  .hide(mCotegoryFragment)
                  .hide(mBoutiqueFragment)
                  .show(mFragment)
                  .commit();
    }

    private void initView() {
      btns  = new RadioButton[5];
        btns[1] = boutique;
        btns[2] = category;
        btns[0] = newGoods;
        btns[4] = personalCenter;
        btns[3] = cart;
    }


    public  void onCheckedChange(View view){
          switch (view.getId()){
              case R.id.layout_boutique:
                  index=1;
                  break;
              case  R.id.layout_category:
                  index=2;
                  break;
              case  R.id.layout_new_goods:
                  index=0;
                  break;
              case R.id.layout_personal_center:
                if (FuLiCenterApplication.getUserAvatar()==null){
                    Intent intent = new Intent(this,LoginActivity.class);
                    startActivityForResult(intent,I.REQUST_CODE_LOGIN);
                }else {
                  index=4;
                }
                  break;
              case R.id.layout_cart:
                  index=3;
                  break;
          }
        choice();

    }

    private void choice() {
        if (index!=currentindex){
            if (mFrtagments[index]==null){
               return;
            }
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(mFrtagments[currentindex]);
            if (!mFrtagments[index].isAdded()){
                ft.add(R.id.fragment_container,mFrtagments[index]);
            }
            ft.show(mFrtagments[index]).commit();
        }
        currentindex=index;
        setRadioButtonStatus();
    }

    private void setRadioButtonStatus() {
        for (int i = 0;i<btns.length;i++){
            if (i==index){
                btns[i].setChecked(true);
            }
            else{
                btns[i].setChecked(false);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        time =FuLiCenterApplication.getInstance().getTime();
        if (time!=0&&FuLiCenterApplication.getUserAvatar()!=null){
            index = 4;
        }else {
            index = 0;
        }
        time++;
        if (index == 4&& FuLiCenterApplication.getUserAvatar()==null){
            index = 0;
        }
       choice();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        SystemClock.sleep(500);
//        if (requestCode == I.REQUST_CODE_LOGIN&&FuLiCenterApplication.getUserAvatar()!=null){
//            index = 4;
//        }
//    }
}
