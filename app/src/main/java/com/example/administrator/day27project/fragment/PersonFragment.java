package com.example.administrator.day27project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.day27project.FuLiCenterApplication;
import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.activity.LoginActivity;
import com.example.administrator.day27project.activity.MainActivity;
import com.example.administrator.day27project.activity.PersonselfActivity;
import com.example.administrator.day27project.bean.MessageBean;
import com.example.administrator.day27project.bean.UserAvatar;
import com.example.administrator.day27project.net.NetDao;
import com.example.administrator.day27project.net.OkHttpUtils;
import com.example.administrator.day27project.utils.ImageLoader;
import com.example.administrator.day27project.utils.MFGT;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/10/24.
 */
public class PersonFragment extends BaseFragment {
    @Bind(R.id.m_Persion_Message)
    ImageView mPersionMessage;
    @Bind(R.id.m_Persion_Setting)
    TextView mPersionSetting;
    @Bind(R.id.m_Persion_UserAvatar)
    ImageView mPersionUserAvatar;
    @Bind(R.id.m_Persion_UserNick)
    TextView mPersionUserNick;
    @Bind(R.id.m_Persion_Collect_Treasure)
    TextView mPersionCollectTreasure;
    @Bind(R.id.m_Persion_Collect_Shop)
    TextView mPersionCollectShop;
    @Bind(R.id.m_Persion_Footprint)
    TextView mPersionFootprint;
    @Bind(R.id.m_Persion_My_All_Card_Iv)
    ImageView mPersionMyAllCardIv;
    @Bind(R.id.m_Persion_My_Online_Shop_IV)
    ImageView mPersionMyOnlineShopIV;
    @Bind(R.id.m_Persion_My_Member_Card_IV)
    ImageView mPersionMyMemberCardIV;

    MainActivity mContext;
    @Bind(R.id.m_Persion_See_Buyed_Treasure)
    LinearLayout mPersionSeeBuyedTreasure;
    @Bind(R.id.m_Persion_My_All_Card)
    RelativeLayout mPersionMyAllCard;
    @Bind(R.id.m_Persion_My_Live_Card)
    RelativeLayout mPersionMyLiveCard;
    @Bind(R.id.m_Persion_My_Online_Shop)
    RelativeLayout mPersionMyOnlineShop;
    @Bind(R.id.m_Persion_My_Member_Card)
    RelativeLayout mPersionMyMemberCard;
    UserAvatar user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_person, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getContext();
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(int action) {
        UserAvatar user = FuLiCenterApplication.getUserAvatar();
        if (user == null) {
            MFGT.startActivity(mContext, LoginActivity.class);
        } else {
            mPersionUserNick.setText(user.getMuserNick());
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, mPersionUserAvatar);
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.m_Persion_Setting)
    public void onClick() {
        startActivity(new Intent(mContext,PersonselfActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
       user = FuLiCenterApplication.getUserAvatar();
        if (user != null) {
            mPersionUserNick.setText(user.getMuserNick());
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, mPersionUserAvatar);
            setCollectNumber();
        }
    }
    private void setCollectNumber() {
        NetDao.getCollectNumber(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result!=null&&result.isSuccess()){
                    mPersionCollectTreasure.setText(result.getMsg());
                }else {
                    mPersionCollectTreasure.setText(String.valueOf(0));
                }
            }

            @Override
            public void onError(String error) {
                mPersionCollectTreasure.setText(String.valueOf(0));
            }
        });
    }
}
