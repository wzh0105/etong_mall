package cn.xiaocool.android_etong.bean.Mine;

import android.app.Application;
import android.os.Handler;

/**
 * Created by 潘 on 2016/8/5.
 */
public class MyApp extends Application {
    // 共享变量
    private Handler handler = null;

    // set方法
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    // get方法
    public Handler getHandler() {
        return handler;
    }
}
