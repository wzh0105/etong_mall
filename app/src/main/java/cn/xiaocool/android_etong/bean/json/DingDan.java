package cn.xiaocool.android_etong.bean.json;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/6/23.
 */
public class DingDan implements Serializable {
    private String userId="";
    private String serviceid="";
    private String carOwnerid="";
    private String shopId="";
    private String price="";
    private String orderstatus="";
    private String time="";
    private String status="";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public String getCarOwnerid() {
        return carOwnerid;
    }

    public void setCarOwnerid(String carOwnerid) {
        this.carOwnerid = carOwnerid;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}
