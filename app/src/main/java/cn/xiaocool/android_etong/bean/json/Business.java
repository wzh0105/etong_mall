package cn.xiaocool.android_etong.bean.json;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by JustYu on 2015/6/21.
 */

      /* "uid": "1",
        "poster": "",
        "name": "云助",
        "phone": "0",
        "longitude": "131.5",
        " ": "167.6",
        "address": "南大街",
        "abstract": "测试测试",
        "mbusinesstime": "7",
        "ebusinesstime": "19",
        "authenticationid": "1",
        "orderid": "0",
        "servicelist": [
        {
        "name": "内部清理"
        },
        {
        "name": "空调清理"
        },
        {
        "name": "换轮胎"
        }
        ],
        "status": "0"*/

public class Business implements Serializable {
    public int uid;
    public String poster;
    public String name;
    public  int phone;
    public  float longitude;
    public float latitude;
    public String address;
    public String desc;
    public  float mbusinesstime;
    public float ebusinesstime;
    public int authenticationid;
    public ArrayList<BusinessService> BService;

    public ArrayList<BusinessService> getBService() {
        return BService;
    }

    public void setBService(ArrayList<BusinessService> BService) {
        this.BService = BService;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float getMbusinesstime() {
        return mbusinesstime;
    }

    public void setMbusinesstime(float mbusinesstime) {
        this.mbusinesstime = mbusinesstime;
    }

    public float getEbusinesstime() {
        return ebusinesstime;
    }

    public void setEbusinesstime(float ebusinesstime) {
        this.ebusinesstime = ebusinesstime;
    }

    public int getAuthenticationid() {
        return authenticationid;
    }

    public void setAuthenticationid(int authenticationid) {
        this.authenticationid = authenticationid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Business(ArrayList<BusinessService> BService, int uid, String poster, String name, int phone, float longitude, float latitude, String address, String desc, float mbusinesstime, float ebusinesstime, int authenticationid) {
        super();
        this.BService = BService;
        this.uid = uid;
        this.poster = poster;
        this.name = name;
        this.phone = phone;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.desc = desc;
        this.mbusinesstime = mbusinesstime;
        this.ebusinesstime = ebusinesstime;
        this.authenticationid = authenticationid;
    }

    public Business(){
        super();
    }

}
