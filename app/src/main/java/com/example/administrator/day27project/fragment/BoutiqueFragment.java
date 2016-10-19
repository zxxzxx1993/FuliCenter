package com.example.administrator.day27project.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.activity.MainActivity;
import com.example.administrator.day27project.adapter.BoutiqueAdapter;
import com.example.administrator.day27project.bean.BoutiqueBean;
import com.example.administrator.day27project.bean.NewGoodsBean;
import com.example.administrator.day27project.net.NetDao;
import com.example.administrator.day27project.net.OkHttpUtils;
import com.example.administrator.day27project.utils.CommonUtils;
import com.example.administrator.day27project.utils.ConvertUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by Administrator on 2016/10/19.
 */
public class BoutiqueFragment extends Fragment {
    @Bind(R.id.tv_refresh)
    TextView tvRefresh;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
  LinearLayoutManager glm;
    MainActivity mcontext;
    BoutiqueAdapter  mBoutiqueAdapter;
    ArrayList<BoutiqueBean> mList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_newgoods, container, false);
        ButterKnife.bind(this, layout);
        mList = new ArrayList<>();
        mcontext = (MainActivity) getContext();
        mBoutiqueAdapter = new BoutiqueAdapter(mcontext,mList);
        initView();
        initData(I.ACTION_DOWNLOAD);
        setListener();
        return layout;
    }

    private void setListener() {
        setPullDownlistener();
        setPullUplistener();
    }

    private void setPullUplistener() {
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItemPosition = glm.findLastVisibleItemPosition();
                if (newState==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition>=mBoutiqueAdapter.getItemCount()-1 && mBoutiqueAdapter.isMore()){
                    initData(I.ACTION_PULL_UP);
                }
            }
        });
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

    private void initData(final int action) {
        NetDao.downloadBoutique(mcontext,new OkHttpUtils.OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                if (result!=null&&result.length>0){
                    srl.setRefreshing(false);
                    tvRefresh.setVisibility(View.GONE);
                    ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
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
    private void initView() {
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
