package com.example.administrator.day27project.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.day27project.FuLiCenterApplication;
import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.activity.CollectActivity;
import com.example.administrator.day27project.activity.MainActivity;
import com.example.administrator.day27project.adapter.CollectAdapter;
import com.example.administrator.day27project.bean.CollectBean;
import com.example.administrator.day27project.bean.UserAvatar;
import com.example.administrator.day27project.net.NetDao;
import com.example.administrator.day27project.net.OkHttpUtils;
import com.example.administrator.day27project.utils.CommonUtils;
import com.example.administrator.day27project.utils.ConvertUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectFragment extends Fragment {

    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
    GridLayoutManager glm;
    MainActivity mcontext;
    CollectAdapter mGoodsAdapter;
    ArrayList<CollectBean> mlist;
    int pageId = 1;
    @Bind(R.id.tv_refresh)
    TextView tvRefresh;
    CollectActivity mContext;
    String username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View layout = inflater.inflate(R.layout.fragment_newgoods, container, false);
        ButterKnife.bind(this, layout);
        mContext = (CollectActivity) getContext();
        mlist = new ArrayList<>();
        mGoodsAdapter = new CollectAdapter(mContext,mlist);
        initView();
        initData(0);
        setListener();
        return  layout;
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
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition >= mGoodsAdapter.getItemCount() - 1 && mGoodsAdapter.isMore()) {
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
                pageId = 1;
                srl.setRefreshing(true);
                tvRefresh.setVisibility(View.VISIBLE);
                initData(I.ACTION_PULL_DOWN);
            }
        });
    }

    protected void initData(final int action) {
        UserAvatar userAvatar = FuLiCenterApplication.getUserAvatar();
        if (userAvatar!=null){
         username  = userAvatar.getMuserName();
        }

        NetDao.getCollect(mContext,username ,pageId, new OkHttpUtils.OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {

                if (result != null && result.length > 0) {
                    srl.setRefreshing(false);
                    tvRefresh.setVisibility(View.GONE);
                    ArrayList<CollectBean> list = ConvertUtils.array2List(result);
                    mGoodsAdapter.setMore(list != null && list.size() > 0);
                    if (list != null && list.size() < 10) {
                        mGoodsAdapter.setFootView("没有更多数据");
                    } else {
                        mGoodsAdapter.setFootView("加载更多数据");
                    }
                    if (action == I.ACTION_PULL_UP) {
                        mGoodsAdapter.addData(list);
                    } else {
                        mGoodsAdapter.initData(list);
                    }

                }
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
        glm = new GridLayoutManager(mcontext, I.COLUM_NUM);
        rv.setLayoutManager(glm);
        rv.setHasFixedSize(true);
        rv.setAdapter(mGoodsAdapter);
    }


}
