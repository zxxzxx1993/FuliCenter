package com.example.administrator.day27project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.activity.CategoryChildActivity;
import com.example.administrator.day27project.bean.CategoryChildBean;
import com.example.administrator.day27project.bean.CategoryGroupBean;
import com.example.administrator.day27project.utils.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/20.
 */
public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;

    public CategoryAdapter(Context mContext, ArrayList<CategoryGroupBean> GroupList, ArrayList<ArrayList<CategoryChildBean>> ChildList) {
        this.mContext = mContext;
        mGroupList = new ArrayList<>();
        mGroupList.addAll(GroupList);
        mChildList = new ArrayList<>();
        mChildList.addAll(ChildList);
    }

    @Override
    public int getGroupCount() {
        return mGroupList != null ? mGroupList.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList != null && mChildList.get(groupPosition) != null ? mChildList.get(groupPosition).size() : 0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return mGroupList != null ? mGroupList.get(groupPosition) : null;
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        return mChildList != null && mChildList.get(groupPosition) != null ? mChildList.get(groupPosition).get(childPosition) : null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_group, null);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        CategoryGroupBean group = getGroup(groupPosition);
        if (group != null) {
            ImageLoader.downloadImg(mContext, holder.ivGroup, group.getImageUrl());
            holder.tvGroupname.setText(group.getName());
            holder.ivIsExpand.setImageResource(isExpanded ? R.mipmap.expand_off : R.mipmap.expand_on);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_grouchild, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        CategoryChildBean child = getChild(groupPosition, childPosition);
        if (child != null) {
            ImageLoader.downloadImg(mContext, holder.ivChild, child.getImageUrl());
            holder.tvChildname.setText(child.getName());
            holder.linearLayout.setTag(child.getId());
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void initData(ArrayList<CategoryGroupBean> GroupList, ArrayList<ArrayList<CategoryChildBean>> ChildList) {
        if (mGroupList!=null){
            mGroupList.clear();
        }
        mGroupList.addAll(GroupList);
        if (mChildList!=null){
            mChildList.clear();
        }
        mChildList.addAll(ChildList);
        notifyDataSetChanged();
    }


    static class GroupViewHolder {
        @Bind(R.id.ivGroup)
        ImageView ivGroup;
        @Bind(R.id.tvGroupname)
        TextView tvGroupname;
        @Bind(R.id.ivIs_expand)
        ImageView ivIsExpand;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


     class ChildViewHolder {
        @Bind(R.id.iv_child)
        ImageView ivChild;
        @Bind(R.id.tv_childname)
        TextView tvChildname;
        @Bind(R.id.lin_child)
        LinearLayout linearLayout;
        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
            int tag = (int) linearLayout.getTag();
                    mContext.startActivity(new Intent(mContext, CategoryChildActivity.class).putExtra(I.Boutique.CAT_ID,tag));
                }
            });
        }
    }
}
