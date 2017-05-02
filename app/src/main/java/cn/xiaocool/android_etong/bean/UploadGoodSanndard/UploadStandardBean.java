package cn.xiaocool.android_etong.bean.UploadGoodSanndard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzh on 2016/7/30.
 */
public class UploadStandardBean implements Serializable {


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

    public static class DataBean implements Serializable {
        private String id;
        private String termid;
        private String name;
        private String listorder;

        private List<PlistBean> plist;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTermid() {
            return termid;
        }

        public void setTermid(String termid) {
            this.termid = termid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getListorder() {
            return listorder;
        }

        public void setListorder(String listorder) {
            this.listorder = listorder;
        }

        public List<PlistBean> getPlist() {
            return plist;
        }

        public void setPlist(List<PlistBean> plist) {
            this.plist = plist;
        }

        public static class PlistBean implements Serializable {
            private String id;
            private String typeid;
            private String name;
            private String listorder;

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

            public String getListorder() {
                return listorder;
            }

            public void setListorder(String listorder) {
                this.listorder = listorder;
            }
        }
    }
}
