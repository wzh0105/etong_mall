package cn.xiaocool.android_etong.bean.Shop;

import java.util.List;

/**
 * Created by 潘 on 2016/8/17.
 */
public class Detail {

    /**
     * status : success
     * data : [{"id":"6","orderid":"870","receivetype":"2","userid":"586","content":"这款产品很好","photo":"","attitudescore":"5","finishscore":"5","effectscore":"5","add_time":"1471421454","status":"1","user_info":[{"name":"jj6","photo":"avatar20160811151106586.png"}]}]
     */

    private String status;
    /**
     * id : 6
     * orderid : 870
     * receivetype : 2
     * userid : 586
     * content : 这款产品很好
     * photo :
     * attitudescore : 5
     * finishscore : 5
     * effectscore : 5
     * add_time : 1471421454
     * status : 1
     * user_info : [{"name":"jj6","photo":"avatar20160811151106586.png"}]
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
        /**
         * name : jj6
         * photo : avatar20160811151106586.png
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

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



    }
}
