package cn.xiaocool.android_etong.bean.json;

import java.io.Serializable;

/**
 * Created by JustYu on 2015/6/21.
 */
public class BusinessService implements Serializable {
    String name;
    String description;
    String mprice;
    String price;
    String shopname;
   public BusinessService(){ super(); };

    public BusinessService(String name, String description, String mprice, String price, String shopname) {
        super();
        this.name = name;
        this.description = description;
        this.mprice = mprice;
        this.price = price;
        this.shopname = shopname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMprice() {
        return mprice;
    }

    public void setMprice(String mprice) {
        this.mprice = mprice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }
}
