package cn.xiaocool.android_etong.bean.Shop;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 潘 on 2016/7/28.
 */
public class ShoppingCart_StoreName implements Serializable {

    /**
     * status : success
     * data : [{"shopid":"2","shopname":"6","goodslist":[{"id":"9","gid":"32","goodsname":"安全","picture":"goods5861469414823084.jpg,,","description":"这很温馨","oprice":"0","price":"15","number":"1","time":"1469585763"},{"id":"8","gid":"32","goodsname":"安全","picture":"goods5861469414823084.jpg,,","description":"这很温馨","oprice":"0","price":"15","number":"2","time":"1469585595"}]}]
     */

    private String status;
    /**
     * shopid : 2
     * shopname : 6
     * goodslist : [{"id":"9","gid":"32","goodsname":"安全","picture":"goods5861469414823084.jpg,,","description":"这很温馨","oprice":"0","price":"15","number":"1","time":"1469585763"},{"id":"8","gid":"32","goodsname":"安全","picture":"goods5861469414823084.jpg,,","description":"这很温馨","oprice":"0","price":"15","number":"2","time":"1469585595"}]
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

    public static class DataBean implements Serializable {
        private String shopid;
        private String shopname;
        private boolean isChoosed;		//商品是否在购物车中被选中
        /**
         * id : 9
         * gid : 32
         * goodsname : 安全
         * picture : goods5861469414823084.jpg,,
         * description : 这很温馨
         * oprice : 0
         * price : 15
         * number : 1
         * time : 1469585763
         */

        private List<GoodslistBean> goodslist;

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public List<GoodslistBean> getGoodslist() {
            return goodslist;
        }

        public boolean isChoosed() {
            return isChoosed;
        }

        public void setChoosed(boolean isChoosed) {
            this.isChoosed = isChoosed;
        }

        public void setGoodslist(List<GoodslistBean> goodslist) {
            this.goodslist = goodslist;
        }

        public static class GoodslistBean implements Serializable{
            private String id;
            private String gid;
            private String goodsname;
            private String picture;
            private String description;
            private String oprice;
            private String price;
            private String number;
            private String time;
            private String proid;
            private String proname;
            private boolean isChoosed;		//商品是否在购物车中被选中

            public void setProid(String proid){
                this.proid = proid;
            }

            public String getProid(){
                return proid;
            }

            public void setProname(String proname){
                this.proname = proname;
            }

            public String getProname(){
                return proname;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
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

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
            public boolean isChoosed() {
                return isChoosed;
            }
            public void setChoosed(boolean isChoosed) {
                this.isChoosed = isChoosed;
            }
        }
    }
}
