package cn.xiaocool.android_etong.bean.business;

import java.util.List;

/**
 * Created by hzh on 2016/12/28.
 */

public class Turnover {

    /**
     * status : success
     * data : [{"user_id":"598","order_id":"1094","order_num":"ET2016110350113","gid":"117","goodsname":"111","picture":"5861477982483126.jpg,,,,","id":"1094","time":"1478161761","state":"5","type":null,"peoplename":"木头","mobile":"15142389265","price":"11","address":"山东省烟台市芝罘区鲁东大学","number":"5","money":"55","remarks":""}]
     */

    private String status;
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
        /**
         * user_id : 598
         * order_id : 1094
         * order_num : ET2016110350113
         * gid : 117
         * goodsname : 111
         * picture : 5861477982483126.jpg,,,,
         * id : 1094
         * time : 1478161761
         * state : 5
         * type : null
         * peoplename : 木头
         * mobile : 15142389265
         * price : 11
         * address : 山东省烟台市芝罘区鲁东大学
         * number : 5
         * money : 55
         * remarks :
         */

        private String user_id;
        private String order_id;
        private String order_num;
        private String gid;
        private String goodsname;
        private String picture;
        private String id;
        private String time;
        private String state;
        private Object type;
        private String peoplename;
        private String mobile;
        private String price;
        private String address;
        private String number;
        private String money;
        private String remarks;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getGoodsname() {
            return goodsname;
        }

        public void setGoodsname(String goodsname) {
            this.goodsname = goodsname;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public String getPeoplename() {
            return peoplename;
        }

        public void setPeoplename(String peoplename) {
            this.peoplename = peoplename;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }
}
