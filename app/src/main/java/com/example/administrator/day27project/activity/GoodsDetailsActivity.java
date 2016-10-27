package com.example.administrator.day27project.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.day27project.FuLiCenterApplication;
import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.bean.AlbumsBean;
import com.example.administrator.day27project.bean.CartBean;
import com.example.administrator.day27project.bean.GoodsDetailsBean;
import com.example.administrator.day27project.bean.MessageBean;
import com.example.administrator.day27project.bean.UserAvatar;
import com.example.administrator.day27project.net.NetDao;
import com.example.administrator.day27project.net.OkHttpUtils;
import com.example.administrator.day27project.utils.CommonUtils;
import com.example.administrator.day27project.utils.ConvertUtils;
import com.example.administrator.day27project.view.FlowIndicator;
import com.example.administrator.day27project.view.SlideAutoLoopView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class GoodsDetailsActivity extends AppCompatActivity {
    @Bind(R.id.chnia_name)
    TextView china_name;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.sal)
    SlideAutoLoopView slideAutoLoopView;
    @Bind(R.id.english_name)
    TextView english;
    @Bind(R.id.indicator)
    FlowIndicator flowIndicator;
    @Bind(R.id.goods_introduce)
    TextView introduce;
    GoodsDetailsActivity context;
    int goodsId;
    GoodsDetailsBean goodsDetailsBean;
    @Bind(R.id.collect)
    ImageView collect;
    @Bind(R.id.layout_goods_details)
    LinearLayout layoutGoodsDetails;
    UserAvatar userAvatar;
    boolean isCollect = false;
    @Bind(R.id.cart_num)
    TextView cartNum;
    updateCartRecevier  mRecevier;
    IntentFilter intentFilter;
    LocalBroadcastManager broadcastManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        goodsId = getIntent().getIntExtra(I.Goods.KEY_GOODS_ID, 0);
        Log.e("zxxzxx", goodsId + "");
        ButterKnife.bind(this);
        userAvatar= FuLiCenterApplication.getUserAvatar();
        context = this;
        FuLiCenterApplication.getInstance().setTime(0);
        if (goodsId == 0) {
            finish();
        }
        goodsDetailsBean = new GoodsDetailsBean();
        initView();
        initData();
    }

    private void initView() {
        broadcastManager = LocalBroadcastManager.getInstance(context);
        intentFilter = new IntentFilter();
        intentFilter.addAction(I.UPDATE_CART_NUMBER);
        mRecevier = new updateCartRecevier(){
            @Override
            public void onReceive(Context context, Intent intent) {
                NetDao.downloadCart(context, userAvatar.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
                    @Override
                    public void onSuccess(CartBean[] result) {
                        if (result!=null){
                            ArrayList<CartBean> cartBeen = ConvertUtils.array2List(result);
                            cartNum.setText(""+cartBeen.size());
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        };
        broadcastManager.registerReceiver(mRecevier, intentFilter);
    }

    @OnClick(R.id.back)
    public void back() {
        finish();
    }

    private void initData() {
        NetDao.doenloadGoodsDetails(context, goodsId, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                if (result != null) {
                    showDetails(result);
                }
            }

            private void showDetails(GoodsDetailsBean result) {
                english.setText(result.getGoodsEnglishName());
                china_name.setText(result.getGoodsName());
                price.setText(result.getCurrencyPrice());
                introduce.setText(result.getGoodsBrief());
                slideAutoLoopView.startPlayLoop(flowIndicator, getAlbumImgUrl(result), getAlbumImgCount(result));
            }

            private String[] getAlbumImgUrl(GoodsDetailsBean result) {
                String[] urls = new String[]{};
                if (result.getProperties() != null && result.getProperties().length > 0) {
                    AlbumsBean[] albums = result.getProperties()[0].getAlbums();
                    urls = new String[albums.length];
                    for (int i = 0; i < albums.length; i++) {
                        urls[i] = albums[i].getImgUrl();
                    }
                }
                return urls;
            }

            private int getAlbumImgCount(GoodsDetailsBean result) {
                if (result.getProperties() != null && result.getProperties().length > 0) {
                    return result.getProperties()[0].getAlbums().length;
                }
                return 0;
            }

            @Override
            public void onError(String error) {
                finish();
            }
        });
        NetDao.downloadCart(context, userAvatar.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
            @Override
            public void onSuccess(CartBean[] result) {
                if (result!=null){
                    ArrayList<CartBean> cartBeen = ConvertUtils.array2List(result);
                    cartNum.setText(""+cartBeen.size());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @OnClick(R.id.collect)
    public void onClick() {
        userAvatar = FuLiCenterApplication.getUserAvatar();
        String muserName = userAvatar.getMuserName();
        if (!isCollect) {
            NetDao.CollectGoods(context, goodsId, muserName, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result.isSuccess()) {
                        CommonUtils.showShortToast("收藏成功");
                        isCollect = !isCollect;
                        collect.setImageResource(R.mipmap.bg_collect_out);
                    } else {
                        CommonUtils.showShortToast("收藏失败");
                    }
                }

                @Override
                public void onError(String error) {
                    CommonUtils.showShortToast("收藏失败");
                    Log.e("error", error);
                }
            });
        } else {
            NetDao.deleteCollect(context, goodsId, muserName, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result.isSuccess()) {
                        CommonUtils.showShortToast("取消成功");
                        isCollect = !isCollect;
                        collect.setImageResource(R.mipmap.bg_collect_in);
                    } else {
                        CommonUtils.showShortToast("取消失败");
                    }
                }

                @Override
                public void onError(String error) {
                    CommonUtils.showShortToast("删除失败");
                    Log.e("error", error);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IsCollect();
    }

    private void IsCollect() {
        userAvatar = FuLiCenterApplication.getUserAvatar();
        String muserName = userAvatar.getMuserName();
        NetDao.isCollect(context, goodsId, muserName, new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result.isSuccess()) {
                    isCollect = true;
                } else {
                    isCollect = false;
                }
                setColectStatus();
            }

            @Override
            public void onError(String error) {
                setColectStatus();
            }
        });
        setColectStatus();
    }

    private void setColectStatus() {
        if (isCollect) {
            collect.setImageResource(R.mipmap.bg_collect_out);
        } else {
            collect.setImageResource(R.mipmap.bg_collect_in);
        }
    }

    @OnClick(R.id.share)
    public void share() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    @OnClick(R.id.iv_cart)
    public void gotoCart() {
        NetDao.addCart(context, goodsId, userAvatar.getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(I.UPDATE_CART_NUMBER));
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    public class updateCartRecevier extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
        }
    }
}
