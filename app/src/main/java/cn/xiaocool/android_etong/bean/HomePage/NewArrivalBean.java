package cn.xiaocool.android_etong.bean.HomePage;

import java.util.List;

/**
 * Created by wzh on 2016/7/24.
 */
public class NewArrivalBean {

    private String status;


    private List<NewArrivalDataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<NewArrivalDataBean> getData() {
        return data;
    }

    public void setData(List<NewArrivalDataBean> data) {
        this.data = data;
    }

    public static class NewArrivalDataBean {
        private String id;
        private String shopid;
        private String goodsname;
        private String oprice;
        private String price;
        private String unit;
        private String description;
        private String picture;
        private String showid;
        private String address;
        private String longitude;
        private String latitude;
        private String status;
        private String racking;
        private String recommend;
        private String time;
        private String sales;
        private String paynum;
        private String shopname;
        private String artno;
        private String brand;
        private String adtitle;
        private String freight;
        private String pays;
//        private String sall;

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

        public void setGoodsname(String goodsname) {
            this.goodsname = goodsname;
        }

        public String getOprice() {
            return oprice;
        }

        public void setOprice(String oprice) {
            this.oprice = oprice;
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

        public void setSales(String sales) {
            this.sales = sales;
        }

        public String getSales() {
            return sales;
        }

        public void setPayNum(String paynum) {
            this.paynum = paynum;
        }

        public String getPayNum() {
            return paynum;
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

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRacking() {
            return racking;
        }

        public void setRacking(String racking) {
            this.racking = racking;
        }

        public String getRecommend() {
            return recommend;
        }

        public void setRecommend(String recommend) {
            this.recommend = recommend;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getArtno() {
            return artno;
        }

        public void setArtno(String artno) {
            this.artno = artno;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }
        public String getAdtitle() {
            return adtitle;
        }

        public void setAdtitle(String adtitle) {
            this.adtitle = adtitle;
        }
        public String getFreight() {
            return freight;
        }

        public void setFreight(String freight) {
            this.freight = freight;
        }
        public String getPays() {
            return pays;
        }

        public void setPays(String pays) {
            this.pays = pays;
        }
    }
}
