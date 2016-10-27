package com.example.administrator.day27project.net;

import android.content.Context;
import android.util.Log;

import com.example.administrator.day27project.I;
import com.example.administrator.day27project.activity.CategoryChildActivity;
import com.example.administrator.day27project.activity.MainActivity;
import com.example.administrator.day27project.bean.BoutiqueBean;
import com.example.administrator.day27project.bean.CartBean;
import com.example.administrator.day27project.bean.CategoryChildBean;
import com.example.administrator.day27project.bean.CategoryGroupBean;
import com.example.administrator.day27project.bean.CollectBean;
import com.example.administrator.day27project.bean.GoodsDetailsBean;
import com.example.administrator.day27project.bean.MessageBean;
import com.example.administrator.day27project.bean.NewGoodsBean;
import com.example.administrator.day27project.bean.Result;
import com.example.administrator.day27project.utils.MD5;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/17.
 */
public class NetDao {
    public static void downloadNewGoods(Context context, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener){
        OkHttpUtils utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(I.CAT_ID))
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }
    public static void doenloadGoodsDetails(Context context, int goodsId, OkHttpUtils.OnCompleteListener<GoodsDetailsBean> litener){
        OkHttpUtils  utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.GoodsDetails.KEY_GOODS_ID,String.valueOf(goodsId))
                .targetClass(GoodsDetailsBean.class)
                .execute(litener);

    }

    public static void downloadBoutique(Context mcontext, OkHttpUtils.OnCompleteListener<BoutiqueBean[]> onCompleteListener) {
        OkHttpUtils utils = new OkHttpUtils(mcontext);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(onCompleteListener);
    }

    public static void downloadBoutiqueChild(Context context,int catId, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener) {
        OkHttpUtils utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.Boutique.CAT_ID,String.valueOf(catId))
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }

    public static void downloadCategory(Context context, OkHttpUtils.OnCompleteListener<CategoryGroupBean[]> listener) {
        OkHttpUtils utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
    }

    public static void downloadChild(Context context, int id, OkHttpUtils.OnCompleteListener<CategoryChildBean[]> listener) {
        OkHttpUtils utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID,String.valueOf(id))
                .targetClass(CategoryChildBean[].class)
                .execute(listener);
    }

    public static void downloadCategoryChild(CategoryChildActivity mcontext, int catId, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> onCompleteListener) {
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(mcontext);
        utils.setRequestUrl(I.REQUEST_FIND_GOODS_DETAILS)
                .addParam(I.Boutique.CAT_ID,String.valueOf(catId))
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(onCompleteListener);
    }

    public static void register(Context context, String username, String nick, String password, OkHttpUtils.OnCompleteListener<Result> listener) {
        OkHttpUtils<Result> utils =  new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.NICK,nick)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .targetClass(Result.class)
                .post()
                .execute(listener);

    }

    public static void login(Context context, String username, String password, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils =  new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .targetClass(String.class)
                .execute(listener);
    }

    public static void updatenick(Context context, String username, String nick, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils =  new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.NICK,nick)
                .targetClass(String.class)
                .execute(listener);
    }

    public static void updateAvatar(Context context, String username, File file, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils =  new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_AVATAR)
                .addParam(I.NAME_OR_HXID,username)
                .addParam(I.AVATAR_TYPE,"user_avatar")
                .addFile2(file)
                .targetClass(String.class)
                .post()
                .execute(listener);
    }

    public static void getCollectNumber(Context context, String username, OkHttpUtils.OnCompleteListener<MessageBean>  listener) {
        OkHttpUtils<MessageBean>  utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECT_COUNT)
                .addParam(I.Collect.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);

    }
    public static void getCollect(Context context, String username,int pageId, OkHttpUtils.OnCompleteListener<CollectBean[]>  listener) {

        OkHttpUtils<CollectBean[]>  utils = new OkHttpUtils<>(context);

        utils.setRequestUrl(I.REQUEST_FIND_COLLECTS)
                .addParam(I.Collect.USER_NAME,String.valueOf(username))
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(CollectBean[].class)
                .execute(listener);
    }

    public static void deleteCollect(Context context,int goodsId, String username, OkHttpUtils.OnCompleteListener<MessageBean>  listener) {
        OkHttpUtils<MessageBean>  utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_COLLECT)
                .addParam(I.Goods.KEY_GOODS_ID,String.valueOf(goodsId))
                .addParam(I.Collect.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);

    }
    public static void CollectGoods(Context context,int goodsId, String username, OkHttpUtils.OnCompleteListener<MessageBean>  listener) {
        OkHttpUtils<MessageBean>  utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_COLLECT)
                .addParam(I.Goods.KEY_GOODS_ID,String.valueOf(goodsId))
                .addParam(I.Collect.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);

    }
    public static void isCollect(Context context,int goodsId, String username, OkHttpUtils.OnCompleteListener<MessageBean>  listener) {
        OkHttpUtils<MessageBean>  utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_IS_COLLECT)
                .addParam(I.Goods.KEY_GOODS_ID,String.valueOf(goodsId))
                .addParam(I.Collect.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);

    }

    public static void downloadCart(MainActivity mcontext,String username, OkHttpUtils.OnCompleteListener<CartBean[]> onCompleteListener) {
        OkHttpUtils<CartBean[]>  utils = new OkHttpUtils<>(mcontext);
        utils.setRequestUrl(I.REQUEST_FIND_CARTS)
                .addParam(I.Cart.USER_NAME,username)
                .targetClass(CartBean[].class)
                .execute(onCompleteListener);
    }
}
