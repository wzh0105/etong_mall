package cn.xiaocool.android_etong.bean.Mine;

import java.util.List;

/**
 * Created by 潘 on 2016/8/1.
 */
public class PendingPayment {

    /**
     * status : success
     * data : [{"order_num":"ET2016080163913","gid":"33","goodsname":"3","picture":"goods5861469415194927.jpg,,","id":"821","time":"1470033115","state":"1","type":null,"peoplename":"","mobile":"15589521956","price":"120","address":"中国山东省烟台市芝罘区祥尧街","number":"1","money":"120","remarks":"","username":"jj","statusname":"未付款","statusend":"0","evaluate":null},{"order_num":"ET2016073131437","gid":"32","goodsname":"安全","picture":"goods5861469414823084.jpg,,","id":"820","time":"1469966067","state":"1","type":null,"peoplename":"","mobile":"15589521956","price":"15","address":"中国山东省烟台市芝罘区祥尧街","number":"2","money":"30","remarks":"","username":"jj","statusname":"未付款","statusend":"0","evaluate":null}]
     */

    private String status;
    /**
     * order_num : ET2016080163913
     * gid : 33
     * goodsname : 3
     * picture : goods5861469415194927.jpg,,
     * id : 821
     * time : 1470033115
     * state : 1
     * type : null
     * peoplename :
     * mobile : 15589521956
     * price : 120
     * address : 中国山东省烟台市芝罘区祥尧街
     * number : 1
     * money : 120
     * remarks :
     * username : jj
     * statusname : 未付款
     * statusend : 0
     * evaluate : null
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
        private String username;
        private String statusname;
        private String statusend;
        private Object evaluate;
        private String deliverytype;
        private String tracking;

        public void setTracking(String tracking) {
            this.tracking = tracking;
        }

        public String getTracking(){
            return tracking;
        }

        public String getDeliverytype() {
            return deliverytype;
        }

        public void setDeliverytype(String deliverytype) {
            this.deliverytype = deliverytype;

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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getStatusname() {
            return statusname;
        }

        public void setStatusname(String statusname) {
            this.statusname = statusname;
        }

        public String getStatusend() {
            return statusend;
        }

        public void setStatusend(String statusend) {
            this.statusend = statusend;
        }

        public Object getEvaluate() {
            return evaluate;
        }

        public void setEvaluate(Object evaluate) {
            this.evaluate = evaluate;
        }
    }
}
