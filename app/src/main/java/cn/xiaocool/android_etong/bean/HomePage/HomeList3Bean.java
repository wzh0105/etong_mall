package cn.xiaocool.android_etong.bean.HomePage;

import java.util.List;

/**
 * Created by wzh on 2016/8/16.
 */
public class HomeList3Bean {


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
        private String levelthree_name;
        private Object pic;

        public String getLevelthree_name() {
            return levelthree_name;
        }

        public void setLevelthree_name(String levelthree_name) {
            this.levelthree_name = levelthree_name;
        }

        public Object getPic() {
            return pic;
        }

        public void setPic(Object pic) {
            this.pic = pic;
        }
    }
}
