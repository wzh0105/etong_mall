package cn.xiaocool.android_etong.bean.business;

import java.util.List;

/**
 * Created by 潘 on 2016/7/16.
 */
public class remove {

    /**
     * status : success
     * data : [{"id":"1","shopid":"2","goodsname":"d","price":"0","unit":"xx","description":"xx","picture":"goods5861468573179334,,","showid":"0","address":"xx","longitude":"xx","latitude":"1","status":"1","racking":"1","time":"1468573202"},{"id":"2","shopid":"2","goodsname":"嗷嗷","price":"566","unit":"今晚","description":"咯嘛","picture":"goods5861468573516071,,","showid":"0","address":"阿德","longitude":"啦啦啦","latitude":"1","status":"1","racking":"1","time":"1468573546"},{"id":"3","shopid":"2","goodsname":"雨","price":"100000000","unit":"金龙鱼","description":"鲁大","picture":"goods5861468573776187.jpg,,","showid":"0","address":"嗷嗷","longitude":"2222","latitude":"1","status":"1","racking":"1","time":"1468573831"},{"id":"4","shopid":"2","goodsname":"雨","price":"100000000","unit":"金龙鱼","description":"鲁大","picture":"goods5861468573843961.jpg,goods5861468573849820.jpg,goods5861468573856087.jpg","showid":"0","address":"嗷嗷","longitude":"2222","latitude":"1","status":"1","racking":"1","time":"1468573859"}]
     */

    private String status;
    /**
     * id : 1
     * shopid : 2
     * goodsname : d
     * price : 0
     * unit : xx
     * description : xx
     * picture : goods5861468573179334,,
     * showid : 0
     * address : xx
     * longitude : xx
     * latitude : 1
     * status : 1
     * racking : 1
     * time : 1468573202
     */

    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String shopid;
        private String goodsname;
        private String price;
        private String oprice;
        private String unit;
        private String description;
        private String picture;
        private String showid;
        private String address;
        private String longitude;
        private String latitude;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getGoodsname() {
            return goodsname;
        }

        public void setOprice(String oprice){this.oprice = oprice;}

        public void setGoodsname(String goodsname) {
            this.goodsname = goodsname;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getShowid() {
            return showid;
        }

        public void setShowid(String showid) {
            this.showid = showid;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getOprice(){return oprice;}

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }
    }
}
