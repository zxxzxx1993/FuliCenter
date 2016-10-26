package com.example.administrator.day27project.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.day27project.FuLiCenterApplication;
import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.activity.GoodsDetailsActivity;
import com.example.administrator.day27project.bean.CollectBean;
import com.example.administrator.day27project.bean.MessageBean;
import com.example.administrator.day27project.bean.NewGoodsBean;
import com.example.administrator.day27project.dao.SharePrefrenceUtils;
import com.example.administrator.day27project.net.NetDao;
import com.example.administrator.day27project.net.OkHttpUtils;
import com.example.administrator.day27project.utils.CommonUtils;
import com.example.administrator.day27project.utils.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/17.
 */
public class CollectAdapter extends RecyclerView.Adapter {
    Context mcontext;
    List<CollectBean> mlist;
    boolean isMore;
    String footView;



    public void setFootView(String footView) {
        this.footView = footView;
        notifyDataSetChanged();
    }

    public CollectAdapter(Context mcontext, List<CollectBean> list) {
        this.mcontext = mcontext;
        mlist = new ArrayList<>();
        mlist.addAll(list);
    }
    public boolean isMore(){
        return isMore;
    }
    public void setMore(boolean more){
        isMore = more;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(View.inflate(mcontext, R.layout.item_footer, null));
        } else {
            holder = new CollectViewHolder(View.inflate(mcontext, R.layout.item_collect, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==I.TYPE_FOOTER){
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.tvFooter.setText(footView);
        }
        else {
            CollectViewHolder goodsViewHolder = (CollectViewHolder) holder;
            CollectBean goods = mlist.get(position);
            ImageLoader.downloadImg(mcontext,goodsViewHolder.ivGoodsThumb,goods.getGoodsThumb());
            goodsViewHolder.tvGoodsName.setText(goods.getGoodsName());
            goodsViewHolder.layoutGoods.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        return mlist != null ? mlist.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public void initData(ArrayList<CollectBean> list) {
        if (mlist!=null){
            mlist.clear();
        }
        mlist.addAll(list);
        notifyDataSetChanged();
    }
    public void addData(ArrayList<CollectBean> list){
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    class CollectViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @Bind(R.id.tvGoodsName)
        TextView tvGoodsName;
        @Bind(R.id.iv_collect_del)
        ImageView iv_collect_del;
        @Bind(R.id.layout_goods)
        RelativeLayout layoutGoods;

        CollectViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            iv_collect_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String muserName = FuLiCenterApplication.getUserAvatar().getMuserName();

                     final int number = (int) layoutGoods.getTag();
                int tag =    mlist.get(number).getGoodsId();
                    NetDao.deleteCollect(mcontext, tag, muserName, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if (result.isSuccess()){
                                CommonUtils.showShortToast("删除成功");
                                deleteCollect(number);
                            }
                        }

                        private void deleteCollect(int number) {
                            mlist.remove(number);
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                }
            });

        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvFooter)
        TextView tvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }




}
