package cn.xiaocool.android_etong.bean.Shop;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 潘 on 2016/8/19.
 */
public class Property implements Serializable{

    /**
     * status : success
     * data : [{"id":"1","termid":"3","name":"尺码","listorder":"0","plist":[{"id":"1","typeid":"1","name":"S","listorder":"0"},{"id":"2","typeid":"1","name":"M","listorder":"0"},{"id":"3","typeid":"1","name":"L","listorder":"0"}]},{"id":"2","termid":"3","name":"颜色","listorder":"0","plist":[{"id":"4","typeid":"2","name":"红","listorder":"0"},{"id":"5","typeid":"2","name":"绿","listorder":"0"},{"id":"6","typeid":"2","name":"蓝","listorder":"0"}]},{"id":"3","termid":"3","name":"型号","listorder":"0","plist":[{"id":"7","typeid":"3","name":"少女款","listorder":"0"},{"id":"8","typeid":"3","name":"OL款","listorder":"0"}]},{"id":"4","termid":"3","name":"重量","listorder":"0","plist":[{"id":"9","typeid":"4","name":"小型","listorder":"0"},{"id":"10","typeid":"4","name":"大型","listorder":"0"}]}]
     */

    private String status;
    /**
     * id : 1
     * termid : 3
     * name : 尺码
     * listorder : 0
     * plist : [{"id":"1","typeid":"1","name":"S","listorder":"0"},{"id":"2","typeid":"1","name":"M","listorder":"0"},{"id":"3","typeid":"1","name":"L","listorder":"0"}]
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

    public static class DataBean implements Serializable{
        private String id;
        private String termid;
        private String name;
        private String listorder;
        /**
         * id : 1
         * typeid : 1
         * name : S
         * listorder : 0
         */

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
            private String proid;

            public void setProid(String proid){
                this.proid = proid;
            }

            public String getProid(){
                return proid;
            }

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
