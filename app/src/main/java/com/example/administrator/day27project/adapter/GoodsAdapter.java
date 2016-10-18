package com.example.administrator.day27project.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.activity.GoodsDetailsActivity;
import com.example.administrator.day27project.bean.NewGoodsBean;
import com.example.administrator.day27project.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/17.
 */
public class GoodsAdapter extends RecyclerView.Adapter {
    Context mcontext;
    List<NewGoodsBean> mlist;
    @Bind(R.id.ivGoodsThumb)
    ImageView ivGoodsThumb;
    @Bind(R.id.tvGoodsName)
    TextView tvGoodsName;
    @Bind(R.id.tvGoodsPrice)
    TextView tvGoodsPrice;
    @Bind(R.id.layout_goods)
    LinearLayout layoutGoods;
    boolean isMore;
    String footView;

    public void setFootView(String footView) {
        this.footView = footView;
        notifyDataSetChanged();
    }

    public GoodsAdapter(Context mcontext, List<NewGoodsBean> list) {
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
            holder = new GoodsViewHolder(View.inflate(mcontext, R.layout.item_goods, null));
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
                GoodsViewHolder goodsViewHolder = (GoodsViewHolder) holder;
                NewGoodsBean goods = mlist.get(position);
                ImageLoader.downloadImg(mcontext,goodsViewHolder.ivGoodsThumb,goods.getGoodsThumb());
                goodsViewHolder.tvGoodsName.setText(goods.getGoodsName());
                goodsViewHolder.tvGoodsPrice.setText(goods.getCurrencyPrice());
                goodsViewHolder.layoutGoods.setTag(goods.getGoodsId());
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

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mlist!=null){
            mlist.clear();
        }
        mlist.addAll(list);
        notifyDataSetChanged();
    }
    public void addData(ArrayList<NewGoodsBean> list){
        mlist.addAll(list);
        notifyDataSetChanged();
    }

     class GoodsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @Bind(R.id.tvGoodsName)
        TextView tvGoodsName;
        @Bind(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;
        @Bind(R.id.layout_goods)
        LinearLayout layoutGoods;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
             layoutGoods.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     int tag = (int) layoutGoods.getTag();
                     mcontext.startActivity(new Intent(mcontext, GoodsDetailsActivity.class).putExtra(I.Goods.KEY_GOODS_ID,tag));
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
