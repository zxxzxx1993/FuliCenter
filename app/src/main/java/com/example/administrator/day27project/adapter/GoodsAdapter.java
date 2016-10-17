package com.example.administrator.day27project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.bean.NewGoodsBean;
import com.example.administrator.day27project.utils.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    public GoodsAdapter(Context mcontext, List<NewGoodsBean> mlist) {
        this.mcontext = mcontext;
        this.mlist = mlist;
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
            if (getItemViewType(position)==I.TYPE_FOOTER){}
        else {
                GoodsViewHolder goodsViewHolder = (GoodsViewHolder) holder;
                NewGoodsBean goods = mlist.get(position);
                ImageLoader.downloadImg(mcontext,goodsViewHolder.ivGoodsThumb,goods.getGoodsThumb());
                goodsViewHolder.tvGoodsName.setText(goods.getGoodsName());
                goodsViewHolder.tvGoodsPrice.setText(goods.getCurrencyPrice());
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

    static class GoodsViewHolder extends RecyclerView.ViewHolder {
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
