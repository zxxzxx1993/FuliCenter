package com.example.administrator.day27project.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.administrator.day27project.R;
import com.example.administrator.day27project.fragment.NewGoodsFragment;
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
    int index;
    Fragment[] mFrtagments;
    NewGoodsFragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.i(" 哈哈哈哈 ");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initFragment();
    }

    private void initFragment() {
           mFrtagments = new Fragment[5];
           mFragment = new NewGoodsFragment();
          getSupportFragmentManager()
                  .beginTransaction()
                  .add(R.id.fragment_container,mFragment)
                  .show(mFragment)
                  .commit();
    }

    private void initView() {
      btns  = new RadioButton[5];
        btns[0] = boutique;
        btns[1] = category;
        btns[2] = newGoods;
        btns[3] = personalCenter;
        btns[4] = cart;
    }


    public  void onCheckedChange(View view){
          switch (view.getId()){
              case R.id.layout_boutique:
                  index=0;
                  break;
              case  R.id.layout_category:
                  index=1;
                  break;
              case  R.id.layout_new_goods:
                  index=2;
                  break;
              case R.id.layout_personal_center:
                  index=3;
                  break;
              case R.id.layout_cart:
                  index=4;
                  break;
          }
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
}
