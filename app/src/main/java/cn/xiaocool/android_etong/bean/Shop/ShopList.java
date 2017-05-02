package cn.xiaocool.android_etong.bean.Shop;

import java.util.List;

/**
 * Created by 潘 on 2016/7/23.
 */
public class ShopList {

    /**
     * status : success
     * data : [{"id":"1","uid":"1","shopname":"我的昵称","photo":"t.jpg","type":"0","city":"","address":"","idcard":"","businesslicense":"","contactphone":"","id_positive_pic":"","id_opposite_pic":"","license_pic":"","state":"1","create_time":"0","level":3,"favorite":19,"turnover":9384,"ordercount":19,"visits":8374},{"id":"2","uid":"586","shopname":"6","photo":"","type":"0","city":"yantai","address":"avatar5861468824289631.jpg","idcard":"123456789012345678","businesslicense":"123","contactphone":"15589521956","id_positive_pic":"","id_opposite_pic":"","license_pic":"positive_pic5861468248444647.jpg","state":"1","create_time":"0","level":3,"favorite":19,"turnover":9384,"ordercount":19,"visits":8374},{"id":"3","uid":"591","shopname":"小e","photo":"","type":"0","city":"yantai","address":"avatar5911469064715302.jpg","idcard":"140224199008181218","businesslicense":"123","contactphone":"15935244346","id_positive_pic":null,"id_opposite_pic":"","license_pic":"positive_pic5911468594215977.jpg","state":"1","create_time":"0","level":3,"favorite":19,"turnover":9384,"ordercount":19,"visits":8374},{"id":"4","uid":"588","shopname":null,"photo":"","type":"0","city":"yantai","address":"看看","idcard":"123456789012345678","businesslicense":"123","contactphone":"15589521951","id_positive_pic":null,"id_opposite_pic":"","license_pic":"positive_pic5881468632512242.jpg","state":"1","create_time":"0","level":3,"favorite":19,"turnover":9384,"ordercount":19,"visits":8374},{"id":"5","uid":"596","shopname":null,"photo":"","type":"0","city":"yantai","address":"烟台","idcard":"370602199909099090","businesslicense":"123","contactphone":"13181514339","id_positive_pic":null,"id_opposite_pic":"","license_pic":"positive_pic5961468633513068.jpg","state":"1","create_time":"0","level":3,"favorite":19,"turnover":9384,"ordercount":19,"visits":8374},{"id":"6","uid":"598","shopname":"出售pn","photo":"","type":"0","city":"yantai","address":"芝罘区","idcard":"370305199501052814","businesslicense":"123","contactphone":"15853503932","id_positive_pic":null,"id_opposite_pic":"","license_pic":"positive_pic5981468981227834.jpg","state":"1","create_time":"0","level":3,"favorite":19,"turnover":9384,"ordercount":19,"visits":8374}]
     */

    private String status;
    /**
     * id : 1
     * uid : 1
     * shopname : 我的昵称
     * photo : t.jpg
     * type : 0
     * city :
     * address :
     * idcard :
     * businesslicense :
     * contactphone :
     * id_positive_pic :
     * id_opposite_pic :
     * license_pic :
     * state : 1
     * create_time : 0
     * level : 3
     * favorite : 19
     * turnover : 9384
     * ordercount : 19
     * visits : 8374
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
        private String uid;
        private String shopname;
        private String photo;
        private String type;
        private String city;
        private String address;
        private String idcard;
        private String businesslicense;
        private String contactphone;
        private String id_positive_pic;
        private String id_opposite_pic;
        private String license_pic;
        private String state;
        private String create_time;
        private int level;
        private int favorite;
        private int turnover;
        private int ordercount;
        private int visits;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getBusinesslicense() {
            return businesslicense;
        }

        public void setBusinesslicense(String businesslicense) {
            this.businesslicense = businesslicense;
        }

        public String getContactphone() {
            return contactphone;
        }

        public void setContactphone(String contactphone) {
            this.contactphone = contactphone;
        }

        public String getId_positive_pic() {
            return id_positive_pic;
        }

        public void setId_positive_pic(String id_positive_pic) {
            this.id_positive_pic = id_positive_pic;
        }

        public String getId_opposite_pic() {
            return id_opposite_pic;
        }

        public void setId_opposite_pic(String id_opposite_pic) {
            this.id_opposite_pic = id_opposite_pic;
        }

        public String getLicense_pic() {
            return license_pic;
        }

        public void setLicense_pic(String license_pic) {
            this.license_pic = license_pic;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getFavorite() {
            return favorite;
        }

        public void setFavorite(int favorite) {
            this.favorite = favorite;
        }

        public int getTurnover() {
            return turnover;
        }

        public void setTurnover(int turnover) {
            this.turnover = turnover;
        }

        public int getOrdercount() {
            return ordercount;
        }

        public void setOrdercount(int ordercount) {
            this.ordercount = ordercount;
        }

        public int getVisits() {
            return visits;
        }

        public void setVisits(int visits) {
            this.visits = visits;
        }
    }
}
