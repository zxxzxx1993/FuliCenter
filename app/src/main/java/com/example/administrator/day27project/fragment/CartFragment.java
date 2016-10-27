package com.example.administrator.day27project.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.day27project.FuLiCenterApplication;
import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.activity.MainActivity;
import com.example.administrator.day27project.adapter.BoutiqueAdapter;
import com.example.administrator.day27project.adapter.CartAdapter;
import com.example.administrator.day27project.bean.BoutiqueBean;
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_newgoods, container, false);
        ButterKnife.bind(this, layout);
        mList = new ArrayList<>();
        mcontext = (MainActivity) getContext();
        mBoutiqueAdapter = new CartAdapter(mcontext,mList);
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    protected void setListener() {
        setPullDownlistener();
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
        NetDao.downloadCart(mcontext,muserName,new OkHttpUtils.OnCompleteListener<CartBean[]>() {
            @Override
            public void onSuccess(CartBean[] result) {
                if (result!=null&&result.length>0){
                    srl.setRefreshing(false);
                    tvRefresh.setVisibility(View.GONE);
                    ArrayList<CartBean> list = ConvertUtils.array2List(result);
                    mBoutiqueAdapter.setMore(list!=null&&list.size()>0);
                    if (list!=null&&list.size()<10){
                        mBoutiqueAdapter.setFootView("没有更多数据");
                    }
                    else{
                        mBoutiqueAdapter.setFootView("加载更多数据");
                    }
                    if (action==I.ACTION_PULL_UP)
                    {
                        mBoutiqueAdapter.addData(list);
                    }
                    else {
                        mBoutiqueAdapter.initData(list);}

                }
            }

            @Override
            public void onError(String error) {
                srl.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                CommonUtils.showShortToast(error);
                Log.e("error:",error);
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
}
