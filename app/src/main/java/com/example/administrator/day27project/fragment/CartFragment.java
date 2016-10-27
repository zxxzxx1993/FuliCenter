package com.example.administrator.day27project.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.day27project.FuLiCenterApplication;
import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.activity.MainActivity;
import com.example.administrator.day27project.adapter.CartAdapter;
import com.example.administrator.day27project.bean.CartBean;
import com.example.administrator.day27project.net.NetDao;
import com.example.administrator.day27project.net.OkHttpUtils;
import com.example.administrator.day27project.utils.CommonUtils;
import com.example.administrator.day27project.utils.ConvertUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/27.
 */
public class CartFragment extends BaseFragment {
    @Bind(R.id.tv_refresh)
    TextView tvRefresh;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
    LinearLayoutManager glm;
    MainActivity mcontext;
    CartAdapter mBoutiqueAdapter;
    ArrayList<CartBean> mList;
    @Bind(R.id.layout_cart)
    RelativeLayout layoutCart;
    @Bind(R.id.cart_nothing)
    TextView cartNothing;
    @Bind(R.id.taltol_price)
    TextView taltolPrice;
    @Bind(R.id.del_price)
    TextView delPrice;
    updateCartRecevier  mRecevier;
    IntentFilter intentFilter;
    LocalBroadcastManager broadcastManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, layout);
        mList = new ArrayList<>();
        mcontext = (MainActivity) getContext();
        mBoutiqueAdapter = new CartAdapter(mcontext, mList);
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    protected void setListener() {
        setPullDownlistener();
        broadcastManager = LocalBroadcastManager.getInstance(mcontext);
        intentFilter = new IntentFilter();
        intentFilter.addAction(I.REQUEST_UPDATE_CART);
        mRecevier = new updateCartRecevier(){
            @Override
            public void onReceive(Context context, Intent intent) {
                sumPrice();
            }
        };
        broadcastManager.registerReceiver(mRecevier, intentFilter);
        intentFilter = new IntentFilter();
        intentFilter.addAction(I.REQUEST_CART);
        mRecevier = new updateCartRecevier(){
            @Override
            public void onReceive(Context context, Intent intent) {
                initData(0);
            }
        };
        broadcastManager.registerReceiver(mRecevier, intentFilter);
    }


    private void setPullDownlistener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                tvRefresh.setVisibility(View.VISIBLE);
                initData(I.ACTION_PULL_DOWN);
            }
        });
    }

    protected void initData(final int action) {
        String muserName = FuLiCenterApplication.getUserAvatar().getMuserName();
        NetDao.downloadCart(mcontext, muserName, new OkHttpUtils.OnCompleteListener<CartBean[]>() {
            @Override
            public void onSuccess(CartBean[] result) {
                srl.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                if (result != null && result.length > 0) {
                    setCartLayout(true);
                    ArrayList<CartBean> list = ConvertUtils.array2List(result);
                    mList.clear();
                    mList.addAll(list);
                    mBoutiqueAdapter.initData(mList);
                }else {
                setCartLayout(false);}
            }

            @Override
            public void onError(String error) {
                srl.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                CommonUtils.showShortToast(error);
                Log.e("error:", error);
            }
        });

    }

    protected void initView() {
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        glm = new LinearLayoutManager(mcontext);
        rv.setLayoutManager(glm);
        rv.setHasFixedSize(true);
        rv.setAdapter(mBoutiqueAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void setCartLayout(boolean hasCart) {
        cartNothing.setVisibility(hasCart ? View.GONE : View.VISIBLE);
        rv.setVisibility(hasCart ? View.VISIBLE : View.GONE);
        layoutCart.setVisibility(hasCart ? View.VISIBLE : View.GONE);
        sumPrice();
    }

    private void sumPrice() {
        double sumPrice = 0;
        double rankPrice = 0;
        if (mList != null && mList.size() > 0) {
            for (CartBean c : mList) {
                if (c.isChecked()) {
                    sumPrice += getprice(c.getGoods().getCurrencyPrice()) * c.getCount();
                    rankPrice += getprice(c.getGoods().getRankPrice()) * c.getCount();
                }
          taltolPrice.setText("合计：¥"+sumPrice);
                delPrice.setText("节省：¥"+rankPrice);
            }
        } else {
            taltolPrice.setText("合计：¥ 0");
            delPrice.setText("节省：¥ 0");
        }
    }

    private int getprice(String price) {
        price = price.substring(price.indexOf("￥") + 1);
        return Integer.valueOf(price);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData(0);
    }
    public class updateCartRecevier extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRecevier!=null){
            mcontext.unregisterReceiver(mRecevier);
        }
    }
}
