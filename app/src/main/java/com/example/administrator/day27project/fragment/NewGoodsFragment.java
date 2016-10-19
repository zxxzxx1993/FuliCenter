package com.example.administrator.day27project.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.activity.MainActivity;
import com.example.administrator.day27project.adapter.GoodsAdapter;
import com.example.administrator.day27project.bean.NewGoodsBean;
import com.example.administrator.day27project.net.NetDao;
import com.example.administrator.day27project.net.OkHttpUtils;
import com.example.administrator.day27project.utils.CommonUtils;
import com.example.administrator.day27project.utils.ConvertUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends BaseFragment {


    @Bind(R.id.tv_refresh)
    TextView tvRefresh;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
    GridLayoutManager glm;
    MainActivity mcontext;
    GoodsAdapter mGoodsAdapter;
    ArrayList<NewGoodsBean> mlist;
    int pageId = 1;
    public NewGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_newgoods, container, false);
        ButterKnife.bind(this, layout);
        mcontext = (MainActivity) getContext();
        mlist = new ArrayList<>();
        mGoodsAdapter = new GoodsAdapter(mcontext,mlist);
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    protected void setListener() {
        setPullDownlistener();
        setPullUplistener();
    }

    private void setPullUplistener() {
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItemPosition = glm.findLastVisibleItemPosition();
                if (newState==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition>=mGoodsAdapter.getItemCount()-1 && mGoodsAdapter.isMore()){
                    pageId++;
                    initData(I.ACTION_PULL_UP);
                }
            }
        });
    }

    private void setPullDownlistener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageId=1;
                srl.setRefreshing(true);
                tvRefresh.setVisibility(View.VISIBLE);
                initData(I.ACTION_PULL_DOWN);
            }
        });
    }

    protected void initData(final int action) {
        NetDao.downloadNewGoods(mcontext, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                if (result!=null&&result.length>0){
                    srl.setRefreshing(false);
                    tvRefresh.setVisibility(View.GONE);
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                   mGoodsAdapter.setMore(list!=null&&list.size()>0);
                    if (list!=null&&list.size()<10){
                         mGoodsAdapter.setFootView("没有更多数据");
                    }
                    else{
                        mGoodsAdapter.setFootView("加载更多数据");
                    }
                    if (action==I.ACTION_PULL_UP)
                    {
                        mGoodsAdapter.addData(list);
                    }
                    else {
                        mGoodsAdapter.initData(list);}

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
        glm = new GridLayoutManager(mcontext, I.COLUM_NUM);
        rv.setLayoutManager(glm);
        rv.setHasFixedSize(true);
        rv.setAdapter(mGoodsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
