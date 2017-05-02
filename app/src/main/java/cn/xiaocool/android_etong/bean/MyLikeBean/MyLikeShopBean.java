package cn.xiaocool.android_etong.bean.MyLikeBean;

import java.util.List;

/**
 * Created by wzh on 2016/7/22.
 */
public class MyLikeShopBean {
    /**
     * status : success
     * data : [{"id":"11","userid":"598","title":"","description":"","type":"1","object_id":"27","createtime":"1469093003","price":"123","photo":"goods5861469000545995.jpg,goods5861469000552629.jpg,"},{"id":"1","userid":"598","title":"","description":"","type":"0","object_id":"27","createtime":"1469092462","price":"","photo":""}]
     */

    private String status;
    /**
     * id : 11
     * userid : 598
     * title :
     * description :
     * type : 1
     * object_id : 27
     * createtime : 1469093003
     * price : 123
     * photo : goods5861469000545995.jpg,goods5861469000552629.jpg,
     */

    private List<MyLikeShopDataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MyLikeShopDataBean> getData() {
        return data;
    }

    public void setData(List<MyLikeShopDataBean> data) {
        this.data = data;
    }

    public static class MyLikeShopDataBean {
        private String id;
        private String userid;
        private String title;
        private String description;
        private String type;
        private String object_id;
        private String createtime;
        private String price;
        private String photo;
        private String starLevel;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getTitle() {
            return title;
        }

        public void setStarLevel(String starLevel) {
            this.starLevel = starLevel;
        }
        public String getStarLevel() {
            return starLevel;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getObject_id() {
            return object_id;
        }

        public void setObject_id(String object_id) {
            this.object_id = object_id;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }
}
