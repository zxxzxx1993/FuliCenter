package com.example.administrator.day27project.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.activity.BoutiqueActivity;
import com.example.administrator.day27project.bean.CartBean;
import com.example.administrator.day27project.bean.GoodsDetailsBean;
import com.example.administrator.day27project.utils.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/27.
 */
public class CartAdapter extends RecyclerView.Adapter {
    Context mcontext;
    ArrayList<CartBean> mList;
    String footView;
    boolean isMore;
    boolean isChoice = false;

    public void setFootView(String footView) {
        this.footView = footView;
    }

    public boolean isMore() {
        return isMore;

    }

    public void initData(ArrayList<CartBean> list) {
         mList=list;
        notifyDataSetChanged();
    }

    public CartAdapter(Context context, ArrayList<CartBean> mList) {
        this.mcontext = context;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;

            holder = new CartViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_cart, null));

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            CartViewHolder cartViewHolder = (CartViewHolder) holder;
            final CartBean cartBean = mList.get(position);
            GoodsDetailsBean goods = cartBean.getGoods();
            if (goods!=null){
                ImageLoader.downloadImg(mcontext,cartViewHolder.goodsPicture,goods.getGoodsThumb());
                cartViewHolder.goodsTitle.setText(goods.getGoodsName());
                cartViewHolder.goodsPrice.setText(goods.getCurrencyPrice());
            }
            cartViewHolder.goodsNum.setText("("+cartBean.getCount()+")");
            cartBean.setChecked(false);
            cartViewHolder.ivIschoice.setImageResource(R.mipmap.checkbox_normal);
             LocalBroadcastManager.getInstance(mcontext).sendBroadcast(new Intent(I.REQUEST_UPDATE_CART));
            cartViewHolder.ivIschoice.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size()  : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return I.TYPE_ITEM;
    }


  class CartViewHolder  extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_ischoice)
        ImageView ivIschoice;
        @Bind(R.id.goods_picture)
        ImageView goodsPicture;
        @Bind(R.id.goods_title)
        TextView goodsTitle;
        @Bind(R.id.goods_src)
        ImageView goodsSrc;
        @Bind(R.id.goods_num)
        TextView goodsNum;
        @Bind(R.id.goods_delete)
        ImageView goodsDelete;
        @Bind(R.id.goods_price)
        TextView goodsPrice;
        @Bind(R.id.lin_cart)
        LinearLayout linCart;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            ivIschoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) ivIschoice.getTag();
                     if (!isChoice){
                           ivIschoice.setImageResource(R.mipmap.checkbox_pressed);
                          isChoice = !isChoice;
                         mList.get(tag).setChecked(true);
                         LocalBroadcastManager.getInstance(mcontext).sendBroadcast(new Intent(I.REQUEST_UPDATE_CART));
                     }else {
                         isChoice = !isChoice;
                         ivIschoice.setImageResource(R.mipmap.checkbox_normal);
                         mList.get(tag).setChecked(false);
                         LocalBroadcastManager.getInstance(mcontext).sendBroadcast(new Intent(I.REQUEST_UPDATE_CART));
                     }

                }
            });
        }
    }
}
