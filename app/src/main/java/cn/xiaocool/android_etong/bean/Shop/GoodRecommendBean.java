package cn.xiaocool.android_etong.bean.Shop;

import java.util.List;

/**
 * Created by wzh on 2016/10/15.
 */

public class GoodRecommendBean {

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
        private String artno;
        private String shopid;
        private String brand;
        private String goodsname;
        private String adtitle;
        private String oprice;
        private String price;
        private String unit;
        private String description;
        private String content;
        private String cpiclist;
        private String picture;
        private String showid;
        private String address;
        private String longitude;
        private String latitude;
        private String freight;
        private String status;
        private String pays;
        private String sall;
        private String racking;
        private String recommend;
        private String hottype;
        private String ise;
        private String isprice;
        private String isnew;
        private String islike;
        private String time;


        private List<ShopListBean> shop_list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getArtno() {
            return artno;
        }

        public void setArtno(String artno) {
            this.artno = artno;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getGoodsname() {
            return goodsname;
        }

        public void setGoodsname(String goodsname) {
            this.goodsname = goodsname;
        }

        public String getAdtitle() {
            return adtitle;
        }

        public void setAdtitle(String adtitle) {
            this.adtitle = adtitle;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCpiclist() {
            return cpiclist;
        }

        public void setCpiclist(String cpiclist) {
            this.cpiclist = cpiclist;
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

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getFreight() {
            return freight;
        }

        public void setFreight(String freight) {
            this.freight = freight;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPays() {
            return pays;
        }

        public void setPays(String pays) {
            this.pays = pays;
        }

        public String getSall() {
            return sall;
        }

        public void setSall(String sall) {
            this.sall = sall;
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

        public String getHottype() {
            return hottype;
        }

        public void setHottype(String hottype) {
            this.hottype = hottype;
        }

        public String getIse() {
            return ise;
        }

        public void setIse(String ise) {
            this.ise = ise;
        }

        public String getIsprice() {
            return isprice;
        }

        public void setIsprice(String isprice) {
            this.isprice = isprice;
        }

        public String getIsnew() {
            return isnew;
        }

        public void setIsnew(String isnew) {
            this.isnew = isnew;
        }

        public String getIslike() {
            return islike;
        }

        public void setIslike(String islike) {
            this.islike = islike;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<ShopListBean> getShop_list() {
            return shop_list;
        }

        public void setShop_list(List<ShopListBean> shop_list) {
            this.shop_list = shop_list;
        }

        public static class ShopListBean {
            private String id;
            private String uid;
            private String shopname;
            private String level;
            private String photo;
            private String type;
            private String city;
            private String address;
            private String idcard;
            private String businesslicense;
            private String contactphone;
            private Object id_positive_pic;
            private String id_opposite_pic;
            private String license_pic;
            private String state;
            private String create_time;

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

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
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

            public void setId_positive_pic(Object id_positive_pic) {
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
        }
    }
}
