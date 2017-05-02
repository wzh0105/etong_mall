package cn.xiaocool.android_etong.bean.business;

import java.util.List;

/**
 * Created by wzh on 2016/10/26.
 */

public class ShopMyEvaluateBean {


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
        private String orderid;
        private String receivetype;
        private String userid;
        private String content;
        private String photo;
        private String attitudescore;
        private String finishscore;
        private String effectscore;
        private String add_time;
        private String status;


        private List<GoodsInfoBean> goods_info;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getReceivetype() {
            return receivetype;
        }

        public void setReceivetype(String receivetype) {
            this.receivetype = receivetype;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getAttitudescore() {
            return attitudescore;
        }

        public void setAttitudescore(String attitudescore) {
            this.attitudescore = attitudescore;
        }

        public String getFinishscore() {
            return finishscore;
        }

        public void setFinishscore(String finishscore) {
            this.finishscore = finishscore;
        }

        public String getEffectscore() {
            return effectscore;
        }

        public void setEffectscore(String effectscore) {
            this.effectscore = effectscore;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<GoodsInfoBean> getGoods_info() {
            return goods_info;
        }

        public void setGoods_info(List<GoodsInfoBean> goods_info) {
            this.goods_info = goods_info;
        }

        private String goodsname;
        private String picture;

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
        public static class GoodsInfoBean {
            private String goodsname;
            private String picture;

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
        }
    }
}
