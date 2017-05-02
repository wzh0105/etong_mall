package cn.xiaocool.android_etong.bean.business;

import java.util.List;

/**
 * Created by wzh on 2016/11/20.
 */

public class EAgencyShopBean {

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


        private String id;
        private String uid;
        private String shopname;
        private String islocal;
        private String level;
        private String vip;
        private String photo;
        private String type;
        private String city;
        private String address;
        private String idcard;
        private String businesslicense;
        private String contactphone;
        private String id_positive_pic;
        private String id_opposite_pic;
        private String license_pic;
        private String state;
        private String create_time;
        private List<?> goodslist;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getIslocal() {
            return islocal;
        }

        public void setIslocal(String islocal) {
            this.islocal = islocal;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getVip() {
            return vip;
        }

        public void setVip(String vip) {
            this.vip = vip;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getBusinesslicense() {
            return businesslicense;
        }

        public void setBusinesslicense(String businesslicense) {
            this.businesslicense = businesslicense;
        }

        public String getContactphone() {
            return contactphone;
        }

        public void setContactphone(String contactphone) {
            this.contactphone = contactphone;
        }

        public Object getId_positive_pic() {
            return id_positive_pic;
        }

        public void setId_positive_pic(String id_positive_pic) {
            this.id_positive_pic = id_positive_pic;
        }

        public String getId_opposite_pic() {
            return id_opposite_pic;
        }

        public void setId_opposite_pic(String id_opposite_pic) {
            this.id_opposite_pic = id_opposite_pic;
        }

        public String getLicense_pic() {
            return license_pic;
        }

        public void setLicense_pic(String license_pic) {
            this.license_pic = license_pic;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public List<?> getGoodslist() {
            return goodslist;
        }

        public void setGoodslist(List<?> goodslist) {
            this.goodslist = goodslist;
        }
    }
}
