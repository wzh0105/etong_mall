package cn.xiaocool.android_etong.bean.json;

import java.io.Serializable;

/**
 * Created by JustYu on 2015/6/21.
 */
/*{
        "uid": "4",
        "brandid": "1",
        "licencenumber": "1",
        "cityid": "1",
        "ownerid": "1",
        "status": "1"
        }*/
public class Car  implements Serializable {

   public int uid;
   public int brandid;
   public String licencenumber;
   public Car(){super();}
    public Car(int uid, int brandid, String licencenumber) {
        super();
        this.uid = uid;
        this.brandid = brandid;
        this.licencenumber = licencenumber;
    }

    public int getUid() {

        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getBrandid() {
        return brandid;
    }

    public void setBrandid(int brandid) {
        this.brandid = brandid;
    }

    public String getLicencenumber() {
        return licencenumber;
    }

    public void setLicencenumber(String licencenumber) {
        this.licencenumber = licencenumber;
    }
}
