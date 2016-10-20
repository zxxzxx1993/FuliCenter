package com.example.administrator.day27project.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.administrator.day27project.R;
import com.example.administrator.day27project.activity.MainActivity;
import com.example.administrator.day27project.adapter.CategoryAdapter;
import com.example.administrator.day27project.bean.CategoryChildBean;
import com.example.administrator.day27project.bean.CategoryGroupBean;
import com.example.administrator.day27project.net.NetDao;
import com.example.administrator.day27project.net.OkHttpUtils;
import com.example.administrator.day27project.utils.ConvertUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CotegoryFragment extends BaseFragment {

    @Bind(R.id.elv)
    ExpandableListView elv;
    CategoryAdapter mCategoryAdapter;
    MainActivity mContext;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;
    int Groupcount;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_cotegory, container, false);
        ButterKnife.bind(this, inflate);
        mContext = (MainActivity) getContext();
        mGroupList = new ArrayList<>();
        mChildList = new ArrayList<>();
        mCategoryAdapter = new CategoryAdapter(mContext,mGroupList,mChildList);
        super.onCreateView(inflater, container, savedInstanceState);
        return inflate;
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(int action) {
        NetDao.downloadCategory(mContext, new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if (result!=null&&result.length>0){
                    ArrayList<CategoryGroupBean> categoryGroupBeen = ConvertUtils.array2List(result);
                    mGroupList.addAll(categoryGroupBeen);
                    for (int i=0;i<categoryGroupBeen.size();i++){
                        mChildList.add(new ArrayList<CategoryChildBean>());
                        downloadChild(categoryGroupBeen.get(i).getId(),i);
                    }
                }
            }

            private void downloadChild(int id, final int index) {
                NetDao.downloadChild(mContext, id, new OkHttpUtils.OnCompleteListener<CategoryChildBean[]>() {
                    @Override
                    public void onSuccess(CategoryChildBean[] result) {
                        Groupcount++;
                        if (result!=null&&result.length>0){
                            ArrayList<CategoryChildBean> categoryChildBeen = ConvertUtils.array2List(result);
                            mChildList.set(index,categoryChildBeen);
                        }
                        if (Groupcount==mGroupList.size())
                        {
                            mCategoryAdapter.initData(mGroupList,mChildList);
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    protected void initView() {
        elv.setGroupIndicator(null);
        elv.setAdapter(mCategoryAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
