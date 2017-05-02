package cn.xiaocool.android_etong.bean.Mine;

import java.util.List;

/**
 * Created by 潘 on 2016/11/20.
 */

public class WithdrawRecord {

    /**
     * status : success
     * data : [{"id":"1","userid":"586","money":"100","banktype":"2","balance":"0","state":"0","lastadmin":"0","time":"1479638524"}]
     */

    private String status;
    /**
     * id : 1
     * userid : 586
     * money : 100
     * banktype : 2
     * balance : 0
     * state : 0
     * lastadmin : 0
     * time : 1479638524
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
        private String userid;
        private String money;
        private String banktype;
        private String balance;
        private String state;
        private String lastadmin;
        private String time;

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

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getBanktype() {
            return banktype;
        }

        public void setBanktype(String banktype) {
            this.banktype = banktype;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getLastadmin() {
            return lastadmin;
        }

        public void setLastadmin(String lastadmin) {
            this.lastadmin = lastadmin;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
