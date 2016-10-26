package com.example.administrator.day27project.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.day27project.FuLiCenterApplication;
import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.bean.AlbumsBean;
import com.example.administrator.day27project.bean.GoodsDetailsBean;
import com.example.administrator.day27project.bean.MessageBean;
import com.example.administrator.day27project.bean.UserAvatar;
import com.example.administrator.day27project.net.NetDao;
import com.example.administrator.day27project.net.OkHttpUtils;
import com.example.administrator.day27project.utils.CommonUtils;
import com.example.administrator.day27project.view.FlowIndicator;
import com.example.administrator.day27project.view.SlideAutoLoopView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        goodsId = getIntent().getIntExtra(I.Goods.KEY_GOODS_ID, 0);
        Log.e("zxxzxx", goodsId + "");
        ButterKnife.bind(this);
        context = this;
        FuLiCenterApplication.getInstance().setTime(0);
        if (goodsId == 0) {
            finish();
        }
        goodsDetailsBean = new GoodsDetailsBean();
        initData();
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
    }

    @OnClick(R.id.collect)
    public void onClick() {
        userAvatar = FuLiCenterApplication.getUserAvatar();
        String muserName = userAvatar.getMuserName();
        if (!isCollect){
         NetDao.CollectGoods(context, goodsId, muserName, new OkHttpUtils.OnCompleteListener<MessageBean>() {
             @Override
             public void onSuccess(MessageBean result) {
                if (result.isSuccess()){
                    CommonUtils.showShortToast("收藏成功");
                    isCollect=!isCollect;
                    collect.setImageResource(R.mipmap.bg_collect_out);
                }else {
                    CommonUtils.showShortToast("收藏失败");
                }
             }

             @Override
             public void onError(String error) {
                 CommonUtils.showShortToast("收藏失败");
                 Log.e("error",error);
             }
         });
     }
        else {
            NetDao.deleteCollect(context, goodsId, muserName, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result.isSuccess()){
                        CommonUtils.showShortToast("取消成功");
                        isCollect=!isCollect;
                        collect.setImageResource(R.mipmap.bg_collect_in);
                    }else {
                        CommonUtils.showShortToast("取消失败");
                    }
                }

                @Override
                public void onError(String error) {
                    CommonUtils.showShortToast("删除失败");
                    Log.e("error",error);
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
                 if (result.isSuccess()){
                     isCollect = true;
                 }else {
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
        if (isCollect){
            collect.setImageResource(R.mipmap.bg_collect_out);
        }else {
            collect.setImageResource(R.mipmap.bg_collect_in);
        }
    }
}
