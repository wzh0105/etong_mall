package cn.xiaocool.android_etong.bean;

import java.util.List;

/**
 * Created by hzh on 2016/12/31.
 */

public class CityBBSBean {
    /**
     * status : success
     * data : [{"mid":"10","type":"1","userid":"598","name":"王志恒","phone":"15853503932","title":"测试0数据","content":"测试一下，发布成功！！","sound":"","create_time":"1483153050","photo":"avatar5981470561563573.jpg","pic":"picture1483153030414.jpg,picture1483153039044.jpg,picture1483153049121.jpg"},{"mid":"9","type":"1","userid":"598","name":"王志恒","phone":"15853503932","title":"测试33333","content":"发布的内容？？？？？","sound":"","create_time":"1483089388","photo":"avatar5981470561563573.jpg","pic":"picture1483089366515.jpg,picture1483089383593.jpg"},{"mid":"8","type":"1","userid":"598","name":"王志恒","phone":"15853503932","title":"15853503932","content":"测试号测试一下","sound":"","create_time":"1483085188","photo":"avatar5981470561563573.jpg","pic":"picture1483084619522.jpg,picture1483085187156.jpg"},{"mid":"7","type":"1","userid":"598","name":"王志恒","phone":"15853503932","title":"15853503932","content":"abcdrdgddx","sound":"","create_time":"1483084454","photo":"avatar5981470561563573.jpg","pic":"picture1483084381619.jpg,picture1483084392768.jpg,picture1483084402355.jpg,picture1483084413490.jpg,picture1483084425902.jpg"},{"mid":"6","type":"1","userid":"598","name":"王志恒","phone":"15853503932","title":"15853503932","content":"abcdrdgddx","sound":"","create_time":"1483084450","photo":"avatar5981470561563573.jpg","pic":"picture1483084381619.jpg,picture1483084392768.jpg,picture1483084402355.jpg,picture1483084413490.jpg,picture1483084425902.jpg"}]
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
         * mid : 10
         * type : 1
         * userid : 598
         * name : 王志恒
         * phone : 15853503932
         * title : 测试0数据
         * content : 测试一下，发布成功！！
         * sound :
         * create_time : 1483153050
         * photo : avatar5981470561563573.jpg
         * pic : picture1483153030414.jpg,picture1483153039044.jpg,picture1483153049121.jpg
         */

        private String mid;
        private String type;
        private String userid;
        private String name;
        private String phone;
        private String title;
        private String content;
        private String sound;
        private String create_time;
        private String photo;
        private String pic;

        private List<LikeBean> like;
        /**
         * userid : 604
         * name : 王志恒
         * content : 哈哈
         * avatar : weixiaotong.png
         * comment_time : 1472718250
         */

        private List<CommentBean> comment;
        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSound() {
            return sound;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public List<LikeBean> getLike() {
            return like;
        }

        public void setLike(List<LikeBean> like) {
            this.like = like;
        }

        public List<CommentBean> getComment() {
            return comment;
        }

        public void setComment(List<CommentBean> comment) {
            this.comment = comment;
        }
    }

    /**
     * status : success
     * data : [{"mid":"9","type":"1","userid":"598","name":"王志恒","phone":"15853503932","title":"测试33333","content":"发布的内容？？？？？","sound":"","create_time":"1483089388","photo":"avatar5981470561563573.jpg","pic":[{"pictureurl":"picture1483089366515.jpg"},{"pictureurl":"picture1483089383593.jpg"}]},{"mid":"8","type":"1","userid":"598","name":"王志恒","phone":"15853503932","title":"15853503932","content":"测试号测试一下","sound":"","create_time":"1483085188","photo":"avatar5981470561563573.jpg","pic":[{"pictureurl":"picture1483084619522.jpg"},{"pictureurl":"picture1483085187156.jpg"}]},{"mid":"7","type":"1","userid":"598","name":"王志恒","phone":"15853503932","title":"15853503932","content":"abcdrdgddx","sound":"","create_time":"1483084454","photo":"avatar5981470561563573.jpg","pic":[{"pictureurl":"picture1483084381619.jpg"},{"pictureurl":"picture1483084392768.jpg"},{"pictureurl":"picture1483084402355.jpg"},{"pictureurl":"picture1483084413490.jpg"},{"pictureurl":"picture1483084425902.jpg"}]},{"mid":"6","type":"1","userid":"598","name":"王志恒","phone":"15853503932","title":"15853503932","content":"abcdrdgddx","sound":"","create_time":"1483084450","photo":"avatar5981470561563573.jpg","pic":[{"pictureurl":"picture1483084381619.jpg"},{"pictureurl":"picture1483084392768.jpg"},{"pictureurl":"picture1483084402355.jpg"},{"pictureurl":"picture1483084413490.jpg"},{"pictureurl":"picture1483084425902.jpg"}]},{"mid":"5","type":"1","userid":"599","name":"菇凉001","phone":"18703422977","title":"","content":"12312312312","sound":"","create_time":"1483018816","photo":"avatar5991470357636503.jpg","pic":[]}]
     */
    public static class LikeBean {
        private String userid;
        private String name;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class CommentBean {
        private String userid;
        private String name;
        private String content;
        private String avatar;
        private String comment_time;
        private String id;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getComment_time() {
            return comment_time;
        }

        public void setComment_time(String comment_time) {
            this.comment_time = comment_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
