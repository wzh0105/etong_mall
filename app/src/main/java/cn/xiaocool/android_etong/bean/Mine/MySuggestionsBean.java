package cn.xiaocool.android_etong.bean.Mine;

import java.util.List;

/**
 * Created by hzh on 2016/12/25.
 */

public class MySuggestionsBean {

    /**
     * status : success
     * data : [{"id":"2","userid":"598","content":"我有意见反馈111111111111111111111","create_time":"1482636786","status":"1","devicestate":"0","reply":[]},{"id":"1","userid":"598","content":"","create_time":"1482634993","status":"1","devicestate":"0","reply":[]}]
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
         * id : 2
         * userid : 598
         * content : 我有意见反馈111111111111111111111
         * create_time : 1482636786
         * status : 1
         * devicestate : 0
         * reply : []
         */

        private String id;
        private String userid;
        private String content;
        private String create_time;
        private String status;
        private String devicestate;
        private List<?> reply;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDevicestate() {
            return devicestate;
        }

        public void setDevicestate(String devicestate) {
            this.devicestate = devicestate;
        }

        public List<?> getReply() {
            return reply;
        }

        public void setReply(List<?> reply) {
            this.reply = reply;
        }
    }
}
