package cn.xiaocool.android_etong.bean.UploadGoodSanndard;

import java.util.List;

/**
 * Created by wzh on 2016/8/3.
 */
public class ObtainAttributeBean {


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
        private String id;
        private String typeid;
        private String name;


        private List<PropertylistBean> propertylist;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<PropertylistBean> getPropertylist() {
            return propertylist;
        }

        public void setPropertylist(List<PropertylistBean> propertylist) {
            this.propertylist = propertylist;
        }

        public static class PropertylistBean {
            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
