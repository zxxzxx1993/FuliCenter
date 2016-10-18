package com.example.administrator.day27project.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.day27project.I;
import com.example.administrator.day27project.R;
import com.example.administrator.day27project.bean.AlbumsBean;
import com.example.administrator.day27project.bean.GoodsDetailsBean;
import com.example.administrator.day27project.net.NetDao;
import com.example.administrator.day27project.net.OkHttpUtils;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        goodsId = getIntent().getIntExtra(I.Goods.KEY_GOODS_ID,0);
        Log.e("zxxzxx",goodsId+"");
        ButterKnife.bind(this);
        context = this;
        if (goodsId==0){
            finish();
        }
        goodsDetailsBean = new GoodsDetailsBean();
        initData();
    }
      @OnClick(R.id.back)
      public void back(){
          finish();
      }
    private void initData() {
        NetDao.doenloadGoodsDetails(context, goodsId, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                  if (result!=null){
                      showDetails(result);
                  }
            }

            private void showDetails(GoodsDetailsBean result) {
                english.setText(result.getGoodsEnglishName());
                china_name.setText(result.getGoodsName());
                price.setText(result.getCurrencyPrice());
                introduce.setText(result.getGoodsBrief());
                slideAutoLoopView.startPlayLoop(flowIndicator,getAlbumImgUrl(result),getAlbumImgCount(result));
            }

            private String[] getAlbumImgUrl(GoodsDetailsBean result) {
                String[] urls = new String[]{};
                if (result.getProperties()!=null&&result.getProperties().length>0){
                    AlbumsBean[] albums = result.getProperties()[0].getAlbums();
                    urls = new String[albums.length];
                    for (int i=0;i<albums.length;i++){
                        urls[i]=albums[i].getImgUrl();
                    }
                }
                return  urls;
            }

            private int getAlbumImgCount(GoodsDetailsBean result) {
                if (result.getProperties()!=null&&result.getProperties().length>0){
                    return result.getProperties()[0].getAlbums().length;
                }
                return  0;
            }

            @Override
            public void onError(String error) {
               finish();
            }
        });
    }
}
