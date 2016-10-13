package com.example.administrator.day27project.bean;

/**
 * Created by Administrator on 2016/10/13.
 */
public class CollectBean {

    /**
     *
     "id": 7672,//主键
     "userName": 7672,// 收藏用户id
     "goodsId": 7672,// 商品id
     "goodsName": "趣味煮蛋模具",//商品中文名
     "goodsEnglishName": "Kotobuki",//商品英文名称
     "goodsThumb": 		 	"http://121.197.1.20/images/201507/thumb_img/6372_thumb_G_1437108490316.jpg",//商品缩略图地址
     "goodsImg": "http://121.197.1.20/images/201507/1437108490034171398.jpg",//商品大图
     "addTime": 1442419200000,// 商品上架时间
     */
    private int id;
    private int userName;
    private int goodsId;
    private String goodsName;
    private String goodsEnglishName;
    private String goodsThumb;
    private String goodsImg;
    private long addTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserName() {
        return userName;
    }

    public void setUserName(int userName) {
        this.userName = userName;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsEnglishName() {
        return goodsEnglishName;
    }

    public void setGoodsEnglishName(String goodsEnglishName) {
        this.goodsEnglishName = goodsEnglishName;
    }

    public String getGoodsThumb() {
        return goodsThumb;
    }

    public void setGoodsThumb(String goodsThumb) {
        this.goodsThumb = goodsThumb;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public CollectBean() {
    }

    @Override
    public String toString() {
        return "CollectBean{" +
                "id=" + id +
                ", userName=" + userName +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsEnglishName='" + goodsEnglishName + '\'' +
                ", goodsThumb='" + goodsThumb + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", addTime=" + addTime +
                '}';
    }
}
