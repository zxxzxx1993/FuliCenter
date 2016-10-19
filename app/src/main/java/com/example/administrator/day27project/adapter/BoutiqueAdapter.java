package com.example.administrator.day27project.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.activity.BoutiqueActivity;
import com.example.administrator.day27project.activity.GoodsDetailsActivity;
import com.example.administrator.day27project.bean.BoutiqueBean;
import com.example.administrator.day27project.utils.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/19.
 */
public class BoutiqueAdapter extends RecyclerView.Adapter {
    Context mcontext;
    ArrayList<BoutiqueBean> mList;
    String footView;
    boolean isMore;

    public void setFootView(String footView) {
        this.footView = footView;
    }

    public boolean isMore(){
        return isMore;

    }
    public  void initData(ArrayList<BoutiqueBean> list){
        if (mList!=null){
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }
    public void addData(ArrayList<BoutiqueBean> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }
    public void setMore(boolean more) {
        isMore = more;
    }

    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> mList) {
        this.mcontext = context;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new GoodsAdapter.FooterViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_footer, null));
        } else {
            holder = new BoutiqueViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_boutique, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==I.TYPE_FOOTER){
            GoodsAdapter.FooterViewHolder footerViewHolder = (GoodsAdapter.FooterViewHolder) holder;
            footerViewHolder.tvFooter.setText(footView);
        }
        else {
            BoutiqueViewHolder boutiqueViewolder = (BoutiqueViewHolder) holder;
            BoutiqueBean boutiqueBean = mList.get(position);
            ImageLoader.downloadImg(mcontext,boutiqueViewolder.ivBoutique,boutiqueBean.getImageurl());
            boutiqueViewolder.tvBoutiqueTitle.setText(boutiqueBean.getTitle());
            boutiqueViewolder.tvDescription.setText(boutiqueBean.getDescription());
            boutiqueViewolder.tvName.setText(boutiqueBean.getName());
            boutiqueViewolder.linearLayout.setTag(boutiqueBean.getId());
        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

     class BoutiqueViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.lin_boutique)
        LinearLayout linearLayout;
        @Bind(R.id.iv_boutique)
        ImageView ivBoutique;
        @Bind(R.id.tvBoutiqueTitle)
        TextView tvBoutiqueTitle;
        @Bind(R.id.tvDescription)
        TextView tvDescription;
        @Bind(R.id.tvName)
        TextView tvName;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) linearLayout.getTag();
                    mcontext.startActivity(new Intent(mcontext, BoutiqueActivity.class).putExtra(I.Boutique.CAT_ID,tag));
                }
            });
        }
    }
}
