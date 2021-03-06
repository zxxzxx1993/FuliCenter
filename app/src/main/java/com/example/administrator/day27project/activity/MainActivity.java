package com.example.administrator.day27project.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.administrator.day27project.FuLiCenterApplication;
import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.adapter.CategoryAdapter;
import com.example.administrator.day27project.bean.CartBean;
import com.example.administrator.day27project.bean.UserAvatar;
import com.example.administrator.day27project.fragment.BoutiqueFragment;
import com.example.administrator.day27project.fragment.CartFragment;
import com.example.administrator.day27project.fragment.CotegoryFragment;
import com.example.administrator.day27project.fragment.NewGoodsFragment;
import com.example.administrator.day27project.fragment.PersonFragment;
import com.example.administrator.day27project.net.NetDao;
import com.example.administrator.day27project.net.OkHttpUtils;
import com.example.administrator.day27project.utils.ConvertUtils;
import com.example.administrator.day27project.utils.L;

import java.util.ArrayList;

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
    TextView tvCartNum;
    int currentindex=0;
    int time = 0;
    Fragment[] mFrtagments;
    NewGoodsFragment mFragment;
    BoutiqueFragment mBoutiqueFragment;
    CotegoryFragment mCotegoryFragment;
    PersonFragment  mPersonFragment;
    CartFragment mcartFragment;
    MainActivity context;
    UserAvatar userAvatar;
    updateCartRecevier  mRecevier;
    IntentFilter intentFilter;
    LocalBroadcastManager broadcastManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ButterKnife.bind(this);
        initView();
        initFragment();
    }
    public class updateCartRecevier extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
        }
    }
    private void initFragment() {
           mFrtagments = new Fragment[5];
           mFragment = new NewGoodsFragment();
          mBoutiqueFragment = new BoutiqueFragment();
         mCotegoryFragment = new CotegoryFragment();
        mPersonFragment = new PersonFragment();
        mcartFragment = new CartFragment();
        mFrtagments[0] = mFragment;
        mFrtagments[1] = mBoutiqueFragment;
        mFrtagments[2] = mCotegoryFragment;
        mFrtagments[3] = mcartFragment;
        mFrtagments[4] = mPersonFragment;
          getSupportFragmentManager()
                  .beginTransaction()
                 .add(R.id.fragment_container,mFragment)
//                  .add(R.id.fragment_container,mBoutiqueFragment)
//                  .add(R.id.fragment_container,mCotegoryFragment)
//                  .hide(mCotegoryFragment)
//                  .hide(mBoutiqueFragment)
                  .show(mFragment)
                  .commit();
    }

    private void initView() {
        broadcastManager = LocalBroadcastManager.getInstance(context);
        intentFilter = new IntentFilter();
        intentFilter.addAction(I.REQUEST_UPDATE_CART);
        mRecevier = new updateCartRecevier(){
            @Override
            public void onReceive(Context context, Intent intent) {
                updateCartNum();
            }
        };
        broadcastManager.registerReceiver(mRecevier, intentFilter);
      btns  = new RadioButton[5];
        btns[1] = boutique;
        btns[2] = category;
        btns[0] = newGoods;
        btns[4] = personalCenter;
        btns[3] = cart;
        tvCartNum = (TextView) findViewById(R.id.tvCartHint);

    }

   public void updateCartNum(){
       userAvatar = FuLiCenterApplication.getUserAvatar();
       if (userAvatar!=null){
           NetDao.downloadCart(context, userAvatar.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
               @Override
               public void onSuccess(CartBean[] result) {
                   if (result!=null){

                       ArrayList<CartBean> cartBeen = ConvertUtils.array2List(result);
                       tvCartNum.setText(""+cartBeen.size());
                   }
               }

               @Override
               public void onError(String error) {

               }
           }); }
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
                  if (FuLiCenterApplication.getUserAvatar()==null){
                      Intent intent = new Intent(this,LoginActivity.class);
                      startActivityForResult(intent,I.REQUST_CODE_LOGIN);
                      FuLiCenterApplication.getInstance().setTime(3);
                  }else {
                  index=3;
                  }
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
        updateCartNum();
        time =FuLiCenterApplication.getInstance().getTime();
        if (time!=0&&FuLiCenterApplication.getUserAvatar()!=null){
            index = 4;
        }else if (time==3){
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
