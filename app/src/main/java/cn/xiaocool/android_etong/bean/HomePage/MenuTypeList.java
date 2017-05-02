package cn.xiaocool.android_etong.bean.HomePage;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiaocool on 17/2/13.
 */

public class MenuTypeList implements Serializable{

    /**
     * term_id : 1
     * name : 服饰箱包
     * photo : t.jpg
     * ishot : 1
     * haschild : 1
     * childlist : [{"term_id":"9","name":"女装","photo":"t.jpg","ishot":"1","haschild":1,"childlist":[{"term_id":"70","name":"针织/毛衫","photo":"t.jpg","ishot":"1"},{"term_id":"66","name":"上衣","photo":"t.jpg","ishot":"1"},{"term_id":"67","name":"衬衫","photo":"t.jpg","ishot":"1"},{"term_id":"68","name":"T恤","photo":"t.jpg","ishot":"1"},{"term_id":"71","name":"裤装","photo":"t.jpg","ishot":"1"},{"term_id":"72","name":"裙装","photo":"t.jpg","ishot":"1"},{"term_id":"73","name":"大码女装","photo":"t.jpg","ishot":"1"},{"term_id":"74","name":"套装","photo":"t.jpg","ishot":"1"},{"term_id":"75","name":"情侣装","photo":"t.jpg","ishot":"1"},{"term_id":"76","name":"其它","photo":"t.jpg","ishot":"1"}]},{"term_id":"10","name":"女鞋","photo":"t.jpg","ishot":"1","haschild":1,"childlist":[{"term_id":"77","name":"单鞋","photo":"t.jpg","ishot":"1"},{"term_id":"78","name":"靴子","photo":"t.jpg","ishot":"1"},{"term_id":"79","name":"内增高鞋","photo":"t.jpg","ishot":"1"},{"term_id":"80","name":"小白鞋","photo":"t.jpg","ishot":"1"},{"term_id":"81","name":"凉鞋","photo":"t.jpg","ishot":"1"},{"term_id":"82","name":"凉拖","photo":"t.jpg","ishot":"1"},{"term_id":"83","name":"鞋配件","photo":"t.jpg","ishot":"1"}]},{"term_id":"12","name":"箱包","photo":"t.jpg","ishot":"1","haschild":1,"childlist":[{"term_id":"84","name":"女包","photo":"t.jpg","ishot":"1"},{"term_id":"85","name":"男包","photo":"t.jpg","ishot":"1"},{"term_id":"86","name":"功能箱包","photo":"t.jpg","ishot":"1"},{"term_id":"87","name":"双肩包","photo":"t.jpg","ishot":"1"}]},{"term_id":"13","name":"男装","photo":"t.jpg","ishot":"1","haschild":1,"childlist":[{"term_id":"88","name":"上衣","photo":"t.jpg","ishot":"1"},{"term_id":"89","name":"衬衫","photo":"t.jpg","ishot":"1"},{"term_id":"90","name":"T恤","photo":"t.jpg","ishot":"1"},{"term_id":"91","name":"针织/毛衫","photo":"t.jpg","ishot":"1"},{"term_id":"92","name":"裤装","photo":"t.jpg","ishot":"1"},{"term_id":"93","name":"其它","photo":"t.jpg","ishot":"1"}]},{"term_id":"14","name":"运动服饰","photo":"t.jpg","ishot":"1","haschild":1,"childlist":[{"term_id":"94","name":"运动鞋","photo":"t.jpg","ishot":"1"},{"term_id":"95","name":"运动服饰","photo":"t.jpg","ishot":"1"},{"term_id":"96","name":"户外服饰","photo":"t.jpg","ishot":"1"},{"term_id":"97","name":"户外鞋靴","photo":"t.jpg","ishot":"1"}]},{"term_id":"15","name":"内衣裤袜","photo":"t.jpg","ishot":"1","haschild":1,"childlist":[{"term_id":"98","name":"女士内衣裤袜","photo":"t.jpg","ishot":"1"},{"term_id":"99","name":"男士内衣裤袜","photo":"t.jpg","ishot":"1"}]},{"term_id":"16","name":"男鞋","photo":"t.jpg","ishot":"1","haschild":1,"childlist":[{"term_id":"100","name":"单鞋","photo":"t.jpg","ishot":"1"},{"term_id":"101","name":"皮鞋","photo":"t.jpg","ishot":"1"},{"term_id":"102","name":"靴子","photo":"t.jpg","ishot":"1"},{"term_id":"103","name":"凉拖","photo":"t.jpg","ishot":"1"},{"term_id":"104","name":"凉鞋","photo":"t.jpg","ishot":"1"},{"term_id":"105","name":"鞋配件","photo":"t.jpg","ishot":"1"}]}]
     */

    private String term_id;
    private String name;
    private String photo;
    private String ishot;
    private int haschild;
    private List<ChildlistBeanX> childlist;

    public String getTerm_id() {
        return term_id;
    }

    public void setTerm_id(String term_id) {
        this.term_id = term_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getIshot() {
        return ishot;
    }

    public void setIshot(String ishot) {
        this.ishot = ishot;
    }

    public int getHaschild() {
        return haschild;
    }

    public void setHaschild(int haschild) {
        this.haschild = haschild;
    }

    public List<ChildlistBeanX> getChildlist() {
        return childlist;
    }

    public void setChildlist(List<ChildlistBeanX> childlist) {
        this.childlist = childlist;
    }

    public static class ChildlistBeanX implements Serializable{
        /**
         * term_id : 9
         * name : 女装
         * photo : t.jpg
         * ishot : 1
         * haschild : 1
         * childlist : [{"term_id":"70","name":"针织/毛衫","photo":"t.jpg","ishot":"1"},{"term_id":"66","name":"上衣","photo":"t.jpg","ishot":"1"},{"term_id":"67","name":"衬衫","photo":"t.jpg","ishot":"1"},{"term_id":"68","name":"T恤","photo":"t.jpg","ishot":"1"},{"term_id":"71","name":"裤装","photo":"t.jpg","ishot":"1"},{"term_id":"72","name":"裙装","photo":"t.jpg","ishot":"1"},{"term_id":"73","name":"大码女装","photo":"t.jpg","ishot":"1"},{"term_id":"74","name":"套装","photo":"t.jpg","ishot":"1"},{"term_id":"75","name":"情侣装","photo":"t.jpg","ishot":"1"},{"term_id":"76","name":"其它","photo":"t.jpg","ishot":"1"}]
         */

        private String term_id;
        private String name;
        private String photo;
        private String ishot;
        private int haschild;
        private List<ChildlistBean> childlist;

        public String getTerm_id() {
            return term_id;
        }

        public void setTerm_id(String term_id) {
            this.term_id = term_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getIshot() {
            return ishot;
        }

        public void setIshot(String ishot) {
            this.ishot = ishot;
        }

        public int getHaschild() {
            return haschild;
        }

        public void setHaschild(int haschild) {
            this.haschild = haschild;
        }

        public List<ChildlistBean> getChildlist() {
            return childlist;
        }

        public void setChildlist(List<ChildlistBean> childlist) {
            this.childlist = childlist;
        }

        public static class ChildlistBean implements Serializable{
            /**
             * term_id : 70
             * name : 针织/毛衫
             * photo : t.jpg
             * ishot : 1
             */

            private String term_id;
            private String name;
            private String photo;
            private String ishot;

            public String getTerm_id() {
                return term_id;
            }

            public void setTerm_id(String term_id) {
                this.term_id = term_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getIshot() {
                return ishot;
            }

            public void setIshot(String ishot) {
                this.ishot = ishot;
            }
        }
    }
}
