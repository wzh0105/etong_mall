package cn.xiaocool.android_etong.bean.business;

import java.util.List;

/**
 * Created by 潘 on 2016/7/21.
 */
public class OrderList {

    /**
     * status : success
     * data : [{"order_num":"ET2016072182881","gid":"28","goodsname":"卖鼠标","picture":"goods5861469001831957.jpg,goods5861469001841706.jpg,goods5861469001860262.jpg","id":"813","time":"1469108017","state":"1","type":null,"peoplename":"","mobile":"15589521951","price":"66","address":"烟台","number":"1","money":"66","remarks":"王志恒娶媳妇","username":null,"statusname":null,"statusend":null},{"order_num":"ET2016072173610","gid":"27","goodsname":"测试","picture":"goods5861469000545995.jpg,goods5861469000552629.jpg,","id":"812","time":"1469107922","state":"1","type":null,"peoplename":"","mobile":"15589521956","price":"123","address":"大泽山","number":"1","money":"123","remarks":"","username":null,"statusname":null,"statusend":null},{"order_num":"ET2016072141720","gid":"27","goodsname":"测试","picture":"goods5861469000545995.jpg,goods5861469000552629.jpg,","id":"811","time":"1469107896","state":"1","type":null,"peoplename":"","mobile":"15589521956","price":"123","address":"大泽山","number":"1","money":"123","remarks":"","username":null,"statusname":null,"statusend":null},{"order_num":"ET2016072164805","gid":"23","goodsname":"12356487","picture":"goods5861468908713708.jpg,,","id":"810","time":"1469107716","state":"1","type":null,"peoplename":"","mobile":"15589521956","price":"0","address":"dd","number":"1","money":"0","remarks":"","username":null,"statusname":null,"statusend":null},{"order_num":"ET2016072115770","gid":"23","goodsname":"12356487","picture":"goods5861468908713708.jpg,,","id":"809","time":"1469107715","state":"1","type":null,"peoplename":"","mobile":"15589521956","price":"0","address":"dd","number":"1","money":"0","remarks":"","username":null,"statusname":null,"statusend":null},{"order_num":"ET2016072119368","gid":"23","goodsname":"12356487","picture":"goods5861468908713708.jpg,,","id":"808","time":"1469107714","state":"1","type":null,"peoplename":"","mobile":"15589521956","price":"0","address":"dd","number":"1","money":"0","remarks":"","username":null,"statusname":null,"statusend":null},{"order_num":"ET2016072114863","gid":"23","goodsname":"12356487","picture":"goods5861468908713708.jpg,,","id":"807","time":"1469106497","state":"1","type":null,"peoplename":"","mobile":"18653503680","price":"0","address":"1508","number":"2","money":"0","remarks":"需要包装","username":null,"statusname":null,"statusend":null}]
     */

    private String status;
    /**
     * order_num : ET2016072182881
     * gid : 28
     * goodsname : 卖鼠标
     * picture : goods5861469001831957.jpg,goods5861469001841706.jpg,goods5861469001860262.jpg
     * id : 813
     * time : 1469108017
     * state : 1
     * type : null
     * peoplename :
     * mobile : 15589521951
     * price : 66
     * address : 烟台
     * number : 1
     * money : 66
     * remarks : 王志恒娶媳妇
     * username : null
     * statusname : null
     * statusend : null
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
        private Object username;
        private Object statusname;
        private Object statusend;

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

        public Object getUsername() {
            return username;
        }

        public void setUsername(Object username) {
            this.username = username;
        }

        public Object getStatusname() {
            return statusname;
        }

        public void setStatusname(Object statusname) {
            this.statusname = statusname;
        }

        public Object getStatusend() {
            return statusend;
        }

        public void setStatusend(Object statusend) {
            this.statusend = statusend;
        }
    }
}
